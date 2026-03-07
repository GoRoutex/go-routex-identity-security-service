package vn.com.go.routex.identity.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import vn.com.go.routex.identity.security.jwt.JwtProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class IdentitySecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentitySecurityApplication.class, args);
	}

}
