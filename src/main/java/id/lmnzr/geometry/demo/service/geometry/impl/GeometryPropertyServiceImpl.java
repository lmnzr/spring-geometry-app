package id.lmnzr.geometry.demo.service.geometry.impl;

import id.lmnzr.geometry.demo.exception.DataIsEmptyException;
import id.lmnzr.geometry.demo.model.entity.geometry.GeometryProperty;
import id.lmnzr.geometry.demo.repository.geometry.GeometryPropertyRepository;
import id.lmnzr.geometry.demo.service.geometry.GeometryPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeometryPropertyServiceImpl implements GeometryPropertyService {
    @Autowired
    private GeometryPropertyRepository propertyRepository;

    @Override
    public List<GeometryProperty> getAll() {
        List<GeometryProperty> props = propertyRepository.findAllProperty();
        if (props.isEmpty()){
            throw new DataIsEmptyException();
        }
        return props;
    }

    @Override
    public GeometryProperty findById(Integer id) {
        return propertyRepository.findById(id).orElseGet(() -> {
            throw new DataIsEmptyException();
        });
    }
}
