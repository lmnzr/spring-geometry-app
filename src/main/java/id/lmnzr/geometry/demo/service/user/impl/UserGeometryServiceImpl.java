package id.lmnzr.geometry.demo.service.user.impl;

import id.lmnzr.geometry.demo.exception.DataIsEmptyException;
import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import id.lmnzr.geometry.demo.model.entity.user.UserGeometry;
import id.lmnzr.geometry.demo.repository.user.UserGeometryRepository;
import id.lmnzr.geometry.demo.service.user.UserGeometryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGeometryServiceImpl implements UserGeometryService {
    @Autowired
    private UserGeometryRepository userGeometryRepository;

    @Override
    public UserGeometry save(UserGeometry geom) {
        return userGeometryRepository.save(geom);
    }

    @Override
    public List<UserGeometry> findByUsername(String username) {
        List<UserGeometry> geoms = userGeometryRepository.findByUsername(username);
        if (geoms.isEmpty()){
            throw new DataIsEmptyException();
        }
        return geoms;
    }
}
