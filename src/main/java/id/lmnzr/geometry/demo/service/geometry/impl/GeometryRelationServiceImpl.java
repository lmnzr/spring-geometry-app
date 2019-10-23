package id.lmnzr.geometry.demo.service.geometry.impl;

import id.lmnzr.geometry.demo.exception.DataIsEmptyException;
import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import id.lmnzr.geometry.demo.model.entity.geometry.GeometryProperty;
import id.lmnzr.geometry.demo.model.entity.geometry.GeometryPropertyMap;
import id.lmnzr.geometry.demo.model.entity.geometry.GeometryRelation;
import id.lmnzr.geometry.demo.repository.geometry.GeometryPropertyRepository;
import id.lmnzr.geometry.demo.repository.geometry.GeometryRelationRepository;
import id.lmnzr.geometry.demo.repository.geometry.GeometryRepository;
import id.lmnzr.geometry.demo.service.geometry.GeometryRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class GeometryRelationServiceImpl implements GeometryRelationService {

    @Autowired
    private GeometryRepository geometryRepository;

    @Autowired
    private GeometryPropertyRepository propertyRepository;

    @Autowired
    private GeometryRelationRepository relationRepository;

    @Async("threadPoolTaskExecutor")
    @Transactional
    @Override
    public void mapRelation(Integer id) {
        Geometry mainGeom = geometryRepository.retrieve(id).orElseGet(() -> {
            throw new DataIsEmptyException();
        });

        List<Geometry> geoms = geometryRepository.findAllGeometry();
        geoms.forEach(geom -> {
            if (!geom.getId().equals(id)) {
                GeometryRelation relation = new GeometryRelation();
                relationRepository.retrieveId(mainGeom.getId(), geom.getId()).ifPresent(relation::setId);
                relation.setIdMainGeom(mainGeom.getId());
                relation.setIdRelatedGeom(geom.getId());
                relation.setIsrelated(compareProperty(mainGeom, geom));
                relationRepository.save(relation);
            }
        });
    }

    @Async("threadPoolTaskExecutor")
    @Transactional
    @Override
    public void mapRelationAll() {
        List<Geometry> geoms = geometryRepository.findAllGeometry();
        geoms.forEach(geom -> mapRelation(geom.getId()));
    }

    @Override
    public List<Geometry> findGroupOf(Integer id) {
        List<Geometry> geoms = new ArrayList<>();

        List<GeometryRelation> groupof = relationRepository.groupof(id);
        groupof.forEach(entry -> {
            Geometry groupGeom = geometryRepository.retrieve(entry.getIdRelatedGeom()).orElse(null);
            if (groupGeom != null) {
                groupGeom.setPropertyMap(new ArrayList<>());
                geoms.add(groupGeom);
            }
        });
        return geoms;
    }

    @Override
    public List<Geometry> findRelatedTo(Integer id) {
        List<Geometry> geoms = new ArrayList<>();

        List<GeometryRelation> related = relationRepository.releatedto(id);
        related.forEach(entry -> {
            Geometry relatedGeom = geometryRepository.retrieve(entry.getIdMainGeom()).orElse(null);
            if (relatedGeom != null) {
                relatedGeom.setPropertyMap(new ArrayList<>());
                geoms.add(relatedGeom);
            }
        });
        return geoms;
    }

    private Boolean compareProperty(Geometry mainGeom, Geometry relGeom) {
        AtomicBoolean ismatch = new AtomicBoolean(true);

        List<GeometryProperty> props = propertyRepository.findAllProperty();
        props.forEach(prop -> {
            GeometryPropertyMap mainProperty = mainGeom.getPropertyMap().stream()
                    .filter(property -> property.getPropertyId().equals(prop.getId()))
                    .findAny().orElse(null);
            GeometryPropertyMap relProperty = relGeom.getPropertyMap().stream()
                    .filter(property -> property.getPropertyId().equals(prop.getId()))
                    .findAny().orElse(null);

            if (mainProperty != null && relProperty != null) {
                if (prop.getType().equalsIgnoreCase(GeometryProperty.Type.CHOICE.toString()) &&
                        prop.getResponse().equalsIgnoreCase(GeometryProperty.PROBABLE_RESPONSE)) {
                    switch (mainProperty.getPropertyResponse()) {
                        case "YES":
                            if (relProperty.getPropertyResponse().equals("NO")) {
                                ismatch.set(false);
                            }
                            break;
                        case "NO":
                            if (relProperty.getPropertyResponse().equals("YES")) {
                                ismatch.set(false);
                            }
                            break;
                        case "MAYBE":
                            if (!relProperty.getPropertyResponse().equals("MAYBE")) {
                                ismatch.set(false);
                            }
                            break;
                    }
                } else {
                    if (!mainProperty.getPropertyResponse().equalsIgnoreCase(relProperty.getPropertyResponse())) {
                        ismatch.set(false);
                    }
                }
            } else {
                ismatch.set(false);
            }

        });

        return ismatch.get();
    }

}
