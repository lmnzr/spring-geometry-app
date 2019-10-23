package id.lmnzr.geometry.demo.model.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_trail")
@Data
public class UserTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Integer modifierId;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime = new Date();

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

    public void setStatus(UserTrail.Status status) {
        this.status = status.toString();
    }

    public String getStatus() {
        return status;
    }
}
