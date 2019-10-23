package id.lmnzr.geometry.demo.utils;

import id.lmnzr.geometry.demo.constants.SecurityConstants;
import id.lmnzr.geometry.demo.security.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilTest {

    private JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();

    @Test
    void createAndDecodeJWTTest() {

        String jwtAuthority = "ROLE_ADMIN";
        String jwtSubject = "Admin";

        String jwt = jwtTokenUtils.createJWT(
                jwtSubject,
                jwtAuthority
        );

        Claims claims = jwtTokenUtils.decodeJWT(jwt);
        assertEquals(jwtSubject, claims.getSubject());
        assertEquals(jwtAuthority,claims.get(SecurityConstants.TOKEN_CLAIM_AUTHORITY));
    }
}
