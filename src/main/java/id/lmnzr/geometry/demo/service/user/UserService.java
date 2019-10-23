package id.lmnzr.geometry.demo.service.user;

import id.lmnzr.geometry.demo.model.dto.LoginUser;
import id.lmnzr.geometry.demo.model.entity.user.User;

public interface UserService  {
    User findOne(String username);
    User findById(int id);
    void registerUser(LoginUser user);
}
