package id.lmnzr.geometry.demo.model.entity.geometry;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.lmnzr.geometry.demo.util.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "geom_info")
@Data
public class Geometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String areaFormula;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> areaParams;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> areaParamsDesc;

    private Boolean isdeleted = false;

    @OneToMany(mappedBy = "geometry",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<GeometryPropertyMap> propertyMap = new ArrayList<>();

    public void setPropertyMap(List<GeometryPropertyMap> propertyMap){
        for(GeometryPropertyMap entry : propertyMap){
            entry.setGeometry(this);
        }
        this.propertyMap = propertyMap;
    }
}
