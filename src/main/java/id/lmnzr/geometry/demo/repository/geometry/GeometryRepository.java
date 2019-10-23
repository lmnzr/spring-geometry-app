package id.lmnzr.geometry.demo.repository.geometry;

import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GeometryRepository extends CrudRepository<Geometry, Integer> {
    @Query(value = "SELECT * FROM geom_info WHERE isdeleted = 0", nativeQuery = true)
    List<Geometry> findAllGeometry();

    @Query("SELECT g FROM Geometry g WHERE g.isdeleted = 0 AND g.id = ?1")
    Optional<Geometry> retrieve(Integer id);
}
