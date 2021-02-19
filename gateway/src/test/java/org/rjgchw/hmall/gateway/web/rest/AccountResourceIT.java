package org.rjgchw.hmall.gateway.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rjgchw.hmall.common.security.AuthoritiesConstants;
import org.rjgchw.hmall.gateway.GatewayApp;
import org.rjgchw.hmall.gateway.config.TestSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.rjgchw.hmall.gateway.web.rest.AccountResourceIT.TEST_USER_LOGIN;
import static org.rjgchw.hmall.gateway.web.rest.TestUtil.ID_TOKEN;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication;

/**
 * Integration tests for the {@link AccountResource} REST controller.
 */
@AutoConfigureWebTestClient
@WithMockUser(value = TEST_USER_LOGIN)
@SpringBootTest(classes = {GatewayApp.class, TestSecurityConfiguration.class})
public class AccountResourceIT {

    static final String TEST_USER_LOGIN = "test";

    private OidcIdToken idToken;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("groups", Collections.singletonList(AuthoritiesConstants.ADMIN));
        claims.put("sub", "jane");
        claims.put("email", "jane.doe@jhipster.com");
        this.idToken = new OidcIdToken(ID_TOKEN, Instant.now(),
            Instant.now().plusSeconds(60), claims);
    }

    @Test
    public void testGetExistingAccount() {
        webTestClient
            .mutateWith(mockAuthentication(TestUtil.authenticationToken(idToken)))
            .mutateWith(csrf())
            .get().uri("/api/account")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
            .expectBody()
            .jsonPath("$.login").isEqualTo("jane")
            .jsonPath("$.email").isEqualTo("jane.doe@jhipster.com")
            .jsonPath("$.authorities").isEqualTo(AuthoritiesConstants.ADMIN);
    }

    @Test
    public void testGetUnknownAccount() {
        webTestClient.get().uri("/api/account")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is5xxServerError();
    }
}
