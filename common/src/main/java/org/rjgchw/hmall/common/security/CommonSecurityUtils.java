package org.rjgchw.hmall.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * common security util.
 *
 * @author Huangw
 * @date 2021-02-23 17:44
 */
public final class CommonSecurityUtils {

    /**
     * extract principal.
     *
     * @param authentication authentication info
     * @return principal
     */
    public static String extractPrincipal(Authentication authentication) {
        String preferredUsernameKey = "preferred_username";
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication instanceof JwtAuthenticationToken) {
            return (String) ((JwtAuthenticationToken) authentication).getToken().getClaims()
                .get(preferredUsernameKey);
        } else if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            Map<String, Object> attributes = ((DefaultOidcUser) authentication.getPrincipal())
                .getAttributes();
            if (attributes.containsKey(preferredUsernameKey)) {
                return (String) attributes.get(preferredUsernameKey);
            }
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static List<GrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    @SuppressWarnings("unchecked")
    private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        return (Collection<String>) claims
            .getOrDefault("groups", claims.getOrDefault("roles", new ArrayList<>()));
    }

    private static List<GrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        return roles.stream().filter(role -> role.startsWith("ROLE_"))
            .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
