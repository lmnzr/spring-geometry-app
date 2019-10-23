package id.lmnzr.geometry.demo.repository.geometry;

import id.lmnzr.geometry.demo.model.entity.geometry.GeometryRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GeometryRelationRepository extends CrudRepository<GeometryRelation, Integer> {
    @Query("SELECT g.id FROM GeometryRelation g WHERE g.idMainGeom = ?1 AND g.idRelatedGeom = ?2")
    Optional<Integer> retrieveId(Integer mainId,Integer relId);

    @Query("SELECT g FROM GeometryRelation g WHERE g.idMainGeom = ?1 AND g.isrelated = 1")
    List<GeometryRelation> groupof(Integer id);

    @Query("SELECT g FROM GeometryRelation g WHERE g.idRelatedGeom = ?1 AND g.isrelated = 1")
    List<GeometryRelation> releatedto(Integer id);

}
