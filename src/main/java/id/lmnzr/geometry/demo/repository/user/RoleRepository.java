package id.lmnzr.geometry.demo.repository.user;

import id.lmnzr.geometry.demo.model.entity.user.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
