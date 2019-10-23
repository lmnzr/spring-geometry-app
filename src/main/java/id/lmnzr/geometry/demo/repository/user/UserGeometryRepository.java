package id.lmnzr.geometry.demo.repository.user;

import id.lmnzr.geometry.demo.model.entity.user.UserGeometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserGeometryRepository extends CrudRepository<UserGeometry, Integer> {
    @Query("SELECT u FROM UserGeometry u WHERE u.isdeleted = 0 AND u.userName = ?1")
    List<UserGeometry> findByUsername(String username);
}
