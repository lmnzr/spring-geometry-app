package id.lmnzr.geometry.demo.model.entity.geometry;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "geom_property")
@Data
public class GeometryProperty {
    public static final String PROBABLE_RESPONSE = "Yes,Maybe,No";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String property;

    private String type;

    private String response;

    @Getter(AccessLevel.NONE)
    private Boolean isdeleted = false;

    @Getter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime;

    @Getter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedtime;

    public enum Type {

        NUMBER("NUMBER"),
        CHOICE("CHOICE");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public void setType(Type type) {
        this.type = type.toString();
    }

    public String getType() {
        return type;
    }


}
