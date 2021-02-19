package org.rjgchw.hmall.common.web.rest.error;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * @author huangwei
 */
@SpringBootApplication
public class ExceptionTranslatorApplicationMock {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExceptionTranslatorApplicationMock.class).web(WebApplicationType.SERVLET).run(args);
    }

    /**
     * Module for serialization/deserialization of RFC7807 Problem.
     */
    @Bean
    ProblemModule problemModule() {
        return new ProblemModule();
    }

    /**
     * Module for serialization/deserialization of ConstraintViolationProblem.
     */
    @Bean
    ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }

    @EnableWebSecurity
    public static class SecurityConfigurationMock extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .csrf()
                .disable();
        }
    }
}
