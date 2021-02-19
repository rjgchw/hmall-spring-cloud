package org.rjgchw.hmall.common.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;

/**
 * Test class for the {@link SecurityWebUtils} utility class.
 */
public class SecurityWebUtilsUnitTest {

    @Test
    public void should_get_current_user_if_login_with_username_and_password() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        Optional<String> login = SecurityWebUtils.getCurrentUserLogin();
        assertThat(login).contains("admin");
    }

    @Test
    public void should_get_current_user_if_login_with_oauth2() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Map<String, Object> claims = new HashMap<>();
        claims.put("groups", "ROLE_USER");
        claims.put("sub", 123);
        claims.put("preferred_username", "admin");
        OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(),
            Instant.now().plusSeconds(60), claims);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        OidcUser user = new DefaultOidcUser(authorities, idToken);
        OAuth2AuthenticationToken bla = new OAuth2AuthenticationToken(user, authorities, "oidc");
        securityContext.setAuthentication(bla);
        SecurityContextHolder.setContext(securityContext);

        Optional<String> login = SecurityWebUtils.getCurrentUserLogin();

        assertThat(login).contains("admin");
    }

    @Test
    public void should_authenticated_if_login_normally() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityWebUtils.isAuthenticated();
        assertThat(isAuthenticated).isTrue();
    }

    @Test
    public void should_not_be_authenticated_if_login_anonymous() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities));
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityWebUtils.isAuthenticated();
        assertThat(isAuthenticated).isFalse();
    }

    @Test
    public void should_current_user_in_role_if_login_with_user() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user", "user", authorities));
        SecurityContextHolder.setContext(securityContext);

        assertThat(SecurityWebUtils.isCurrentUserInRole(AuthoritiesConstants.USER)).isTrue();
    }

    @Test
    public void should_current_user_not_in_role_if_login_with_admin() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user", "user", authorities));
        SecurityContextHolder.setContext(securityContext);

        assertThat(SecurityWebUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)).isFalse();
    }
}
