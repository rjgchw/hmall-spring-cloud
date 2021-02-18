package org.rjgchw.hmall.order.web.rest.errors;

import org.rjgchw.hmall.common.web.rest.error.AbstractExceptionTranslator;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator extends AbstractExceptionTranslator {

    public ExceptionTranslator(Environment env) {
        super(env);
    }
}
