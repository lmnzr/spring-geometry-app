package id.lmnzr.geometry.demo.model.entity.geometry;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "geom_property_mapping")
@Data
public class GeometryPropertyMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer propertyId;

    private String propertyResponse;

    @Getter(AccessLevel.NONE)
    private Boolean isdeleted = false;

    @Getter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime;

    @Getter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedtime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "geom_id", nullable=false)
    @JsonBackReference
    private Geometry geometry;

}
