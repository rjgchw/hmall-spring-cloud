package org.rjgchw.hmall.common.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.util.context.Context;

/**
 * Test class for the {@link SecurityWebFluxUtils} utility class.
 */
public class SecurityWebFluxUtilsUnitTest {

    @Test
    public void should_get_current_user_if_login_with_username_and_password() {
        String login = SecurityWebFluxUtils.getCurrentUserLogin()
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "admin")
                )
            )
            .block();
        assertThat(login).isEqualTo("admin");
    }

    @Test
    public void should_authenticated_if_login_normally() {
        Boolean isAuthenticated = SecurityWebFluxUtils.isAuthenticated()
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "admin")
                )
            )
            .block();
        assertThat(isAuthenticated).isTrue();
    }

    @Test
    public void should_not_be_authenticated_if_login_anonymous() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        Boolean isAuthenticated = SecurityWebFluxUtils.isAuthenticated()
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "admin", authorities)
                )
            )
            .block();
        assertThat(isAuthenticated).isFalse();
    }

    @Test
    public void should_current_user_in_role_if_login_with_user() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        Context context = ReactiveSecurityContextHolder.withAuthentication(
            new UsernamePasswordAuthenticationToken("admin", "admin", authorities)
        );
        Boolean isCurrentUserInRole = SecurityWebFluxUtils
            .isCurrentUserInRole(AuthoritiesConstants.USER)
            .subscriberContext(context)
            .block();
        assertThat(isCurrentUserInRole).isTrue();
    }

    @Test
    public void should_current_user_not_in_role_if_login_with_admin() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        Context context = ReactiveSecurityContextHolder.withAuthentication(
            new UsernamePasswordAuthenticationToken("admin", "admin", authorities)
        );
        Boolean isCurrentUserInRole = SecurityWebFluxUtils
            .isCurrentUserInRole(AuthoritiesConstants.ADMIN)
            .subscriberContext(context)
            .block();
        assertThat(isCurrentUserInRole).isFalse();
    }

}
