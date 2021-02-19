package org.rjgchw.hmall.common.web.rest.error.translator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.jhipster.web.util.HeaderUtil;
import org.rjgchw.hmall.common.web.rest.error.BadRequestAlertException;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 * @author huangwei
 */
public abstract class AbstractWebExceptionTranslator extends AbstractExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    public AbstractWebExceptionTranslator(Environment env) {
        super(env);
    }

    @Override
    public ResponseEntity<Problem> handleMessageNotReadableException(HttpMessageNotReadableException exception, NativeWebRequest request) {
        String message = null;
        if (exception.getRootCause() instanceof InvalidFormatException) {
            message = "Body should be a JSON object";
        } else if (exception.getRootCause() instanceof JsonParseException) {
            message = "Problems parsing JSON";
        } else {
            message = exception.getMessage();
        }
        Problem problem = Problem.builder()
            .withStatus(Status.BAD_REQUEST)
            .withTitle("Bad Request")
            .with(MESSAGE_KEY, message)
            .build();
        return create(exception, problem, request);
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        return super.process0(entity, request.getNativeRequest(HttpServletRequest.class).getRequestURI());
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        return create(ex, handleBindingResult(ex.getBindingResult()), request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
        return create(ex, request, HeaderUtil.createFailureAlert(getApplicationName(), true, ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        return create(ex, handleConcurrencyFailure(), request);
    }

    @Override
    public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
        return super.prepare0(throwable, status, type);
    }

    @Override
    protected StatusType defaultConstraintViolationStatus0() {
        return defaultConstraintViolationStatus();
    }

    @Override
    protected boolean isCausalChainsEnabled0(){
        return isCausalChainsEnabled();
    }

    @Override
    protected ThrowableProblem toProblem0(final Throwable throwable) {
        return toProblem(throwable);
    }
}
