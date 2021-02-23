package org.rjgchw.hmall.common.security;

import static org.rjgchw.hmall.common.security.CommonSecurityUtils.extractPrincipal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

/**
 * @author Huangw
 * @date 2021-02-23 17:38
 */
public final class SecurityWebFluxUtils {

    private SecurityWebFluxUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Mono<String> getCurrentUserLogin() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .flatMap(authentication -> Mono.justOrEmpty(extractPrincipal(authentication)));
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static Mono<Boolean> isAuthenticated() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getAuthorities)
            .map(authorities -> authorities.stream().map(GrantedAuthority::getAuthority).noneMatch(AuthoritiesConstants.ANONYMOUS::equals));
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static Mono<Boolean> isCurrentUserInRole(String authority) {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getAuthorities)
            .map(authorities -> authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(authority::equals));
    }
}
