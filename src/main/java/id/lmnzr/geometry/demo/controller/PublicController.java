package id.lmnzr.geometry.demo.controller;

import id.lmnzr.geometry.demo.constants.ErrorMessage;
import id.lmnzr.geometry.demo.constants.SuccessMessage;
import id.lmnzr.geometry.demo.model.dto.AuthToken;
import id.lmnzr.geometry.demo.model.dto.LoginUser;
import id.lmnzr.geometry.demo.model.entity.user.User;
import id.lmnzr.geometry.demo.security.JwtTokenUtils;
import id.lmnzr.geometry.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping("")
    private String redirectIndex() {
        return "redirect:page/products";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String register() {
        return "login";
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @GetMapping("/health")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String getMessage() {
        return "Hello from Geometry API Demo";
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/token")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> access(@RequestBody @Valid LoginUser loginUser) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getEmail(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.INVALID_CREDENTIALS);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @PostMapping(value = "/signup")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> signup(@RequestBody @Valid LoginUser user) {
        User existingUser = userService.findOne(user.getEmail());
        if (existingUser == null) {
            userService.registerUser(user);
            return ResponseEntity.ok(SuccessMessage.OPERATION_SUCCESS);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.EXISTING_USER);
        }
    }

}
