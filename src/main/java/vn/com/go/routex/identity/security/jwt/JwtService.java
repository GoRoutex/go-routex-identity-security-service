package vn.com.go.routex.identity.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtService {

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;


    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
    public Claims parseToken(String token) {
        return Jwts.parser()
                .requireIssuer(jwtProperties.getIssuer())
                .verifyWith(secretKey)
                .requireAudience(jwtProperties.getAudience())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
