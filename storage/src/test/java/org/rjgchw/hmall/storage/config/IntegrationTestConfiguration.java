package org.rjgchw.hmall.storage.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import static org.mockito.Mockito.mock;

/**
 * This class allows you to run unit and integration tests without an IdP.
 */
@TestConfiguration
public class IntegrationTestConfiguration {


    @Bean
    JwtDecoder jwtDecoder() {
        return mock(JwtDecoder.class);
    }

}
