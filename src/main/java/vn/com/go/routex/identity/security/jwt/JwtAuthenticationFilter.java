package vn.com.go.routex.identity.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.go.routex.identity.security.identity.IdentityUserContext;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {

            Claims claims = jwtService.parseToken(token);

            String subject = claims.getSubject();
            String username = claims.get("username", String.class);
            String email = claims.get("email", String.class);

            List<String> roleList = claims.get(jwtProperties.getAuthorityClaimName(), List.class);
            Set<String> roles = roleList == null ? Set.of() : new HashSet<>(roleList);

            Collection<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            .map(r -> new SimpleGrantedAuthority(jwtProperties.getAuthorityPrefix() + r))
                            .collect(Collectors.toSet());


            IdentityUserContext userContext = new IdentityUserContext(
                    subject,
                    username,
                    email,
                    roles
            );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userContext,
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception ignored) {
        }
        filterChain.doFilter(request, response);
    }
}
