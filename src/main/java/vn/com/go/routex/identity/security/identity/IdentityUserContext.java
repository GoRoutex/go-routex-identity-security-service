package vn.com.go.routex.identity.security.identity;

import java.util.Set;

public record IdentityUserContext(
        String subject,
        String username,
        String email,
        Set<String> roles
) {
}
