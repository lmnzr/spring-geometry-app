package id.lmnzr.geometry.demo.service.user;

import id.lmnzr.geometry.demo.model.entity.user.UserGeometry;

import java.util.List;

public interface UserGeometryService {
    UserGeometry save(UserGeometry geom);
    List<UserGeometry> findByUsername(String username);
}
