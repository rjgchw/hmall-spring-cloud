package org.rjgchw.hmall.storage.web.rest.errors;

import org.apache.commons.lang3.StringUtils;
import org.rjgchw.hmall.common.web.rest.error.ErrorConstants;
import org.rjgchw.hmall.common.web.rest.error.translator.AbstractWebExceptionTranslator;
import org.rjgchw.hmall.storage.service.error.ProductDoesNotExistException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator extends AbstractWebExceptionTranslator {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ExceptionTranslator(Environment env) {
        super(env);
    }

    @Override
    protected boolean containsPackageName(String message) {
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "org.rjgchw.hmall.storage");
    }

    @Override
    protected String getApplicationName() {
        return applicationName;
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleProductDoesNotExistException(ProductDoesNotExistException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withType(ErrorConstants.ENTITY_NOT_FOUND_TYPE)
            .withTitle(ex.getMessage())
            .withStatus(Status.UNPROCESSABLE_ENTITY)
            .build();
        return create(ex, problem, request);
    }
}
