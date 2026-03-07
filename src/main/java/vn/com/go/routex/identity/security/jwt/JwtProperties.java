package vn.com.go.routex.identity.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "identity.jwt")
public class JwtProperties {
    private String secret;
    private String issuer;
    private String audience = "identity-security";
    private String authorityClaimName = "roles";
    private String authorityPrefix = "ROLE_";
}
