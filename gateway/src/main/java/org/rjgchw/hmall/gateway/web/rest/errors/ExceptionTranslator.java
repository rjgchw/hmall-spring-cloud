package org.rjgchw.hmall.gateway.web.rest.errors;

import org.apache.commons.lang3.StringUtils;
import org.rjgchw.hmall.common.web.rest.error.translator.AbstractWebFluxExceptionTranslator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
@Component("jhiExceptionTranslator")
public class ExceptionTranslator extends AbstractWebFluxExceptionTranslator {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ExceptionTranslator(Environment env) {
        super(env);
    }

    @Override
    protected boolean containsPackageName(String message) {

        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "jhipster");
    }

    @Override
    protected String getApplicationName() {
        return applicationName;
    }
}
