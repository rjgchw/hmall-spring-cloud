package org.rjgchw.hmall.common.security;

import org.rjgchw.hmall.common.config.Constants;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityWebUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
    }
}
