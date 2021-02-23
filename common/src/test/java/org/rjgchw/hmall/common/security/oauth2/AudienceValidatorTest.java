package org.rjgchw.hmall.common.security.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Test class for the {@link AudienceValidator} utility class.
 */
public class AudienceValidatorTest {

    private final AudienceValidator validator = new AudienceValidator(
        Arrays.asList("api://default"));

    @Test
    public void should_has_error_if_audience_invalid() {
        Map<String, String> claims = new HashMap<>();
        claims.put("aud", "bar");
        Jwt badJwt = mock(Jwt.class);
        when(badJwt.getAudience()).thenReturn(new ArrayList<>(claims.values()));
        assertThat(validator.validate(badJwt).hasErrors()).isTrue();
    }

    @Test
    public void should_not_has_error_if_audience_valid() {
        Map<String, String> claims = new HashMap<>();
        claims.put("aud", "api://default");
        Jwt jwt = mock(Jwt.class);
        when(jwt.getAudience()).thenReturn(new ArrayList<>(claims.values()));
        assertThat(validator.validate(jwt).hasErrors()).isFalse();
    }
}
