package id.lmnzr.geometry.demo.service.geometry;

import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;

import java.util.List;

public interface GeometryRelationService {
    void mapRelation(Integer id);
    void mapRelationAll();
    List<Geometry> findGroupOf(Integer id);
    List<Geometry> findRelatedTo(Integer id);
}
