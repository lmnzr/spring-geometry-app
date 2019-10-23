package id.lmnzr.geometry.demo.controller;

import id.lmnzr.geometry.demo.constants.ErrorMessage;
import id.lmnzr.geometry.demo.exception.DataIsEmptyException;
import id.lmnzr.geometry.demo.model.dto.EvalGeometry;
import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import id.lmnzr.geometry.demo.model.entity.user.User;
import id.lmnzr.geometry.demo.model.entity.user.UserGeometry;
import id.lmnzr.geometry.demo.service.geometry.GeometryPropertyService;
import id.lmnzr.geometry.demo.service.geometry.GeometryRelationService;
import id.lmnzr.geometry.demo.service.geometry.GeometryService;
import id.lmnzr.geometry.demo.service.user.UserGeometryService;
import id.lmnzr.geometry.demo.service.user.UserService;
import id.lmnzr.geometry.demo.util.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class GeometryController {
    @Autowired
    private Calculator calculator;

    @Autowired
    private GeometryService geometryService;

    @Autowired
    private UserGeometryService userGeometryService;

    @Autowired
    private UserService userService;

    @Autowired
    private GeometryPropertyService propertyService;

    @Autowired
    private GeometryRelationService relationService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/api/geometries")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllGeometries(){
        Map<String,Object> json = new HashMap<>();
        try{
            List<Geometry> geoms = geometryService.getAll();
            geoms.forEach(geom-> geom.setPropertyMap(new ArrayList<>()));

            json.put("data",geoms);
            return ResponseEntity.ok(json);
        }
        catch (DataIsEmptyException exc){
            json.put("data",null);
            return ResponseEntity.ok(json);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/api/geometries/evaluate")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> evaluateGeometry(@RequestBody @Valid EvalGeometry geom){
        Geometry geometry;
        try{
            geometry = geometryService.findById(geom.getId());
            geom.setArea(calculator.evaluate(geometry.getAreaFormula(),geom.getParameter()));
            geom.setGroupof(relationService.findGroupOf(geom.getId()));
            geom.setRelatedto(relationService.findRelatedTo(geom.getId()));
            return ResponseEntity.ok(geom);
        }
        catch (DataIsEmptyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.DATA_NOT_FOUND);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.INVALID_FUNCTION_PARAMETER);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping("/api/geometries/save")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> saveGeometry(@RequestBody @Valid EvalGeometry geom, Principal principal){
        User user = userService.findOne(principal.getName());

        Map<String,Double> variables = geom.getParameter();

        UserGeometry myGeom = new UserGeometry();
        myGeom.setUserId(user.getId());
        myGeom.setUserName(user.getUsername());
        myGeom.setGeomId(geom.getId());
        myGeom.setGeomName(geom.getName());
        myGeom.setEntrytime(new Date());
        myGeom.setGeomParams(new ArrayList<>(variables.keySet()));
        myGeom.setGeomVars(new ArrayList<>(variables.values()));
        myGeom.setGeomArea(geom.getArea());
        return ResponseEntity.ok(userGeometryService.save(myGeom));
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/api/geometries/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> userGeometries(Principal principal){
        Map<String,Object> json = new HashMap<>();
        try{
            List<UserGeometry> geoms = userGeometryService.findByUsername(principal.getName());
            json.put("data",geoms);
            return ResponseEntity.ok(json);
        }
        catch (DataIsEmptyException exc){
            json.put("data",null);
            return ResponseEntity.ok(json);
        }
    }

}
