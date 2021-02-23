package org.rjgchw.hmall.gateway.config;

import tech.jhipster.config.JHipsterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Hooks;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:37
 */
@Configuration
@Profile("!" + JHipsterConstants.SPRING_PROFILE_PRODUCTION)
public class ReactorConfiguration {
    public ReactorConfiguration() {
        Hooks.onOperatorDebug();
    }
}
