package org.rjgchw.hmall.common.security;

import java.util.Optional;
import org.rjgchw.hmall.common.config.Constants;
import org.springframework.data.domain.AuditorAware;

/**
 * @author Huangw
 * @date 2021-02-23 17:39
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityWebUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
    }
}
