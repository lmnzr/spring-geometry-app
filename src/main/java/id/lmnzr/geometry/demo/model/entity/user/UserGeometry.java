package id.lmnzr.geometry.demo.model.entity.user;

import id.lmnzr.geometry.demo.util.DecimalDoubleConverter;
import id.lmnzr.geometry.demo.util.DoubleListConverter;
import id.lmnzr.geometry.demo.util.StringListConverter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_geom")
@Data
public class UserGeometry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer geomId;

    private String geomName;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> geomParams;

    @Column
    @Convert(converter = DoubleListConverter.class)
    private List<Double> geomVars;

    @Column
    @Convert(converter = DecimalDoubleConverter.class)
    private Double geomArea;

    private Boolean isdeleted = false;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime;

    @Getter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedtime;
}
