package id.lmnzr.geometry.demo.model.entity.geometry;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "geom_relation")
@Data
public class GeometryRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idMainGeom;

    private Integer idRelatedGeom;

    private Boolean isrelated;
}
