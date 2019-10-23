package id.lmnzr.geometry.demo.constants;

public final class SecurityConstants {

    // Signing key for HS512 algorithm
    public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";

    // JWT token defaults
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "secure-api";
    public static final String TOKEN_AUDIENCE = "secure-app";
    public static final Integer TOKEN_VALIDITY = 5*60*60;
    public static final String TOKEN_CLAIM_AUTHORITY = "role";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
