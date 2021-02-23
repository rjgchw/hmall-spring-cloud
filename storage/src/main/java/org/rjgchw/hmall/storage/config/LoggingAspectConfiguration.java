package org.rjgchw.hmall.storage.config;

import org.rjgchw.hmall.storage.aop.logging.LoggingAspect;

import tech.jhipster.config.JHipsterConstants;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

/**
 *
 * @author Huangw
 * @date 2021-02-23 17:37
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
