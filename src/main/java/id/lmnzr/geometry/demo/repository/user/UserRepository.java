package id.lmnzr.geometry.demo.repository.user;

import id.lmnzr.geometry.demo.model.entity.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
