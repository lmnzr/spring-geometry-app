package id.lmnzr.geometry.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.lmnzr.geometry.demo.constants.ErrorMessage;
import id.lmnzr.geometry.demo.model.dto.EvalGeometry;
import id.lmnzr.geometry.demo.model.entity.geometry.Geometry;
import id.lmnzr.geometry.demo.security.JwtTokenUtils;
import id.lmnzr.geometry.demo.service.geometry.GeometryService;
import id.lmnzr.geometry.demo.util.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GeometryControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(GeometryControllerTest.class);

    private static final ObjectMapper om = new ObjectMapper();
    private static final String ADMIN_EMAIl = "admin@admin.com";

    @Value("${SPRING_DB_URL}")
    private String dbUrl;

    @Value("${SPRING_DB_SCHEMA}")
    private String dbSchema;

    @Value("${SPRING_DB_USER}")
    private String dbUser;

    @Value("${SPRING_DB_PASSWORD}")
    private String dbPassword;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private GeometryService geometryService;

    private String token;

    @BeforeEach
    public void init() {
        token = jwtTokenUtils.createJWT(ADMIN_EMAIl, "ROLE_ADMIN");
    }

    @Test
    void getAllGeometries() throws Exception {
        List<Geometry> geoms = geometryService.getAll();
        mockMvc.perform(get("/api/geometries")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(geoms.size())));
        ;
    }

    @Test
    void evaluateGeometryInvalidParameter() throws Exception {
        EvalGeometry geom = new EvalGeometry();
        geom.setId(1);
        geom.setName("square");
        geom.setFormula("s^2");
        Map<String,Double> params = new HashMap<>();
        params.put("s",9D);
        geom.setParameter(params);
        mockMvc.perform(post("/api/geometries/evaluate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(geom)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ErrorMessage.INVALID_FUNCTION_PARAMETER));
        ;
    }

    @Test
    void evaluateGeometryInvalidId() throws Exception {
        EvalGeometry geom = new EvalGeometry();
        geom.setId(99);
        geom.setName("square");
        geom.setFormula("s^2");
        Map<String,Double> params = new HashMap<>();
        params.put("s",9D);
        geom.setParameter(params);
        mockMvc.perform(post("/api/geometries/evaluate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(geom)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ErrorMessage.DATA_NOT_FOUND));
        ;
    }

    @Test
    void evaluateGeometryOk() throws Exception {
        EvalGeometry geom = new EvalGeometry();
        geom.setId(2);
        geom.setName("square");
        geom.setFormula("s^2");
        Map<String,Double> params = new HashMap<>();
        params.put("s",9D);
        geom.setParameter(params);
        mockMvc.perform(post("/api/geometries/evaluate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).
                contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(geom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.area", is(81D)));
        ;
    }

    @Test
    void saveAndGetUserGeometry() throws Exception{
        mockMvc.perform(get("/api/geometries/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(nullValue())))
        ;

        EvalGeometry geom = new EvalGeometry();
        geom.setId(2);
        geom.setName("square");
        geom.setFormula("s^2");
        Map<String,Double> params = new HashMap<>();
        params.put("s",9D);
        geom.setArea(81D);
        geom.setParameter(params);
        mockMvc.perform(post("/api/geometries/save")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token).
                        contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(geom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.geomId", is(2)))
                .andExpect(jsonPath("$.geomName", is("square")))
        ;

        mockMvc.perform(get("/api/geometries/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)));
        ;

        String url = "jdbc:"+dbUrl+"/"+dbSchema;
        String username = dbUser;
        String password = dbPassword;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.createStatement().execute("TRUNCATE TABLE `flyway_schema_history`");
            logger.info("Truncate Table");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

}