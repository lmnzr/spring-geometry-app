package id.lmnzr.geometry.demo.model.dto;

import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class EvalGeometry {
    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String formula;

    @NotNull
    private Map<String,Double> parameter;

    private Double area;

    private List<Geometry> groupof;

    private List<Geometry> relatedto;
}
