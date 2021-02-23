package org.rjgchw.hmall.storage.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import static org.springframework.security.oauth2.jwt.JwtClaimNames.SUB;

/**
 * This class allows you to run unit and integration tests without an IdP.
 */
@TestConfiguration
public class PactTestConfiguration {


    @Bean
    JwtDecoder jwtDecoder() {
        return new JwtDecoder() {
            @Override
            public Jwt decode(String s) throws JwtException {
                return Jwt.withTokenValue("token")
                    .header("alg", "none")
                    .claim(SUB, s)
                    .claim("scope", "read")
                    .claim("preferred_username", s)
                    .build();
            }
        };
    }

}
