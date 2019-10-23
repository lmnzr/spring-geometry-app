package id.lmnzr.geometry.demo.repository.geometry;

import id.lmnzr.geometry.demo.model.entity.geometry.GeometryProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GeometryPropertyRepository extends CrudRepository<GeometryProperty, Integer> {
    @Query(value = "SELECT * FROM geom_property WHERE isdeleted = 0", nativeQuery = true)
    List<GeometryProperty> findAllProperty();

}
