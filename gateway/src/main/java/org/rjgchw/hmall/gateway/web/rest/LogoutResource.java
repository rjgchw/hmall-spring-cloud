package org.rjgchw.hmall.gateway.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:37
 */
@RestController
public class LogoutResource {
    private final Mono<ClientRegistration> registration;

    public LogoutResource(ReactiveClientRegistrationRepository registrations) {
        this.registration = registrations.findByRegistrationId("oidc");
    }

    /**
     * {@code POST  /api/logout} : logout the current user.
     *
     * @param idToken the ID token.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and a body with a global logout URL and ID token.
     */
    @PostMapping("/api/logout")
    public Mono<Map<String, String>> logout(@AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken, WebSession session) {
        return session.invalidate().then(
            this.registration.map(oidc -> oidc.getProviderDetails().getConfigurationMetadata().get("end_session_endpoint").toString())
                .map(logoutUrl -> {
                    Map<String, String> logoutDetails = new HashMap<>(16);
                    logoutDetails.put("logoutUrl", logoutUrl);
                    logoutDetails.put("idToken", idToken.getTokenValue());
                    return logoutDetails;
                })
        );
    }
}
