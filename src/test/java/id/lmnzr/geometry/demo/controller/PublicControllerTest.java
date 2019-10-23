package id.lmnzr.geometry.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.lmnzr.geometry.demo.constants.ErrorMessage;
import id.lmnzr.geometry.demo.model.dto.LoginUser;
import id.lmnzr.geometry.demo.security.JwtTokenUtils;
import id.lmnzr.geometry.demo.util.JsonConverter;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PublicControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(PublicControllerTest.class);

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

    @Test
    public void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
        ;
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
        ;
    }

    @Test
    public void getMessage() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Geometry API Demo"))
                ;
    }

    @Test
    public void accessEmptyRequest() throws Exception {
        mockMvc.perform(post("/token"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("request has empty body or exception occured"))
        ;
    }

    @Test
    public void accessEmptyPasword() throws Exception {
        LoginUser user = new LoginUser();
        user.setEmail(ADMIN_EMAIl);

        List<String> expectedErrros = new ArrayList<>();
        expectedErrros.add("password must not be null");

        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", is(expectedErrros)));
    }

    @Test
    public void accessInvalid() throws Exception {
        LoginUser user = new LoginUser();
        user.setEmail(ADMIN_EMAIl);
        user.setPassword("wrongPassword");

        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ErrorMessage.INVALID_CREDENTIALS))
        ;
    }

    @Test
    public void accessOk() throws Exception {
        LoginUser user = new LoginUser();
        user.setEmail(ADMIN_EMAIl);
        user.setPassword("test");

        String json = om.writeValueAsString(user);

        MvcResult result = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();
        Map mapJson = jsonConverter.stringToMap(result.getResponse().getContentAsString());

        Claims claims = jwtTokenUtils.decodeJWT((String) mapJson.get("token"));
        assertEquals(ADMIN_EMAIl, claims.getSubject());
    }

    @Test
    public void signupEmptyRequest() throws Exception {
        mockMvc.perform(post("/signup"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("request has empty body or exception occured"))
        ;
    }

    @Test
    public void signupEmptyPassword() throws Exception {
        LoginUser user = new LoginUser();
        user.setEmail(ADMIN_EMAIl);

        List<String> expectedErrros = new ArrayList<>();
        expectedErrros.add("password must not be null");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", is(expectedErrros)))
        ;
    }

    @Test
    public void signupExistingUser() throws Exception {
        LoginUser user = new LoginUser();
        user.setEmail(ADMIN_EMAIl);
        user.setPassword("test");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ErrorMessage.EXISTING_USER))
        ;
    }

    @Test
    public void signupOk() throws Exception {
        LoginUser user = new LoginUser();
        user.setEmail("user@user.com");
        user.setPassword("test");

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isOk())
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