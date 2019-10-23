package id.lmnzr.geometry.demo.model.entity.user;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "role_info")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name")
    private String name;

    private String description;
}
