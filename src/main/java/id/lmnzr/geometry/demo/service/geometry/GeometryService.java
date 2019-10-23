package id.lmnzr.geometry.demo.service.geometry;

import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;

import java.util.List;

public interface GeometryService {
    List<Geometry> getAll();
    Geometry findById(Integer id);
    void addOrUpdate(Geometry geometry);


}
