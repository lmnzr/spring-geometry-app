package id.lmnzr.geometry.demo.service.geometry;

import id.lmnzr.geometry.demo.model.entity.geometry.GeometryProperty;

import java.util.List;

public interface GeometryPropertyService {
    List<GeometryProperty> getAll();
    GeometryProperty findById(Integer id);
}
