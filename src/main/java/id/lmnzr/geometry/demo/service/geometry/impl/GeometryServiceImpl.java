package id.lmnzr.geometry.demo.service.geometry.impl;

import id.lmnzr.geometry.demo.exception.DataIsEmptyException;
import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import id.lmnzr.geometry.demo.model.entity.geometry.GeometryTrail;
import id.lmnzr.geometry.demo.repository.geometry.GeometryRepository;
import id.lmnzr.geometry.demo.repository.geometry.GeometryTrailRepository;
import id.lmnzr.geometry.demo.service.geometry.GeometryRelationService;
import id.lmnzr.geometry.demo.service.geometry.GeometryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GeometryServiceImpl implements GeometryService {
    private static final Logger logger = LoggerFactory.getLogger(GeometryServiceImpl.class);

    @Autowired
    private GeometryRelationService relationService;

    @Autowired
    private GeometryRepository geometryRepository;

    @Autowired
    private GeometryTrailRepository geometryTrailRepository;

    @Override
    public List<Geometry> getAll() {
        List<Geometry> geoms = geometryRepository.findAllGeometry();
        if (geoms.isEmpty()){
            throw new DataIsEmptyException();
        }
        return geoms;
    }

    @Override
    public Geometry findById(Integer id) {
        return geometryRepository.retrieve(id).orElseGet(() -> {
            throw new DataIsEmptyException();
        });
    }

    @Transactional
    @Override
    public void addOrUpdate(Geometry geom) {
        GeometryTrail trail = new GeometryTrail();
        trail.setGeomName(geom.getName());
        trail.setEntrytime(new Date());

        if(geom.getId() != null){
            trail.setGeomId(geom.getId());
            if(geom.getIsdeleted()){
                trail.setStatus(GeometryTrail.Status.DELETED);
            } else{
                trail.setStatus(GeometryTrail.Status.UPDATED);
            }
            geometryTrailRepository.save(trail);
            geometryRepository.save(geom);
        } else{
            geometryRepository.save(geom);
            trail.setGeomId(geom.getId());
            trail.setStatus(GeometryTrail.Status.CREATED);
            geometryTrailRepository.save(trail);
        }
        logger.info("Run Geometries Relational Analysis For {}",geom.getName());
        relationService.mapRelation(geom.getId());
    }
}
