package vn.com.go.routex.identity.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .requireIssuer(jwtProperties.getIssuer())
                .verifyWith(getKey())
                .requireAudience(jwtProperties.getAudience())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
