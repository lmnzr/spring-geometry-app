package id.lmnzr.geometry.demo.model.entity.geometry;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "geom_trail")
@Data
public class GeometryTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer geomId;

    private String geomName;

    private Integer idModifier;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime;

    public enum Status {
        CREATED("CREATED"),
        UPDATED("UPDATED"),
        DELETED("DELETED");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public String getStatus() {
        return status;
    }
}
