package org.rjgchw.hmall.common.web.rest.error.translator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.net.URI;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.rjgchw.hmall.common.web.rest.error.BadRequestAlertException;
import org.rjgchw.hmall.common.web.rest.error.ResourceNotFoundAlertException;
import org.rjgchw.hmall.common.web.rest.error.UnprocessableRequestAlertException;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 *
 * @author huangwei
 */
public abstract class AbstractWebExceptionTranslator
    extends AbstractExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    protected AbstractWebExceptionTranslator(Environment env) {
        super(env);
    }

    @Override
    public ResponseEntity<Problem> handleMessageNotReadableException(
        HttpMessageNotReadableException exception, NativeWebRequest request) {
        String message;
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
    public ResponseEntity<Problem> process(
        @Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        return super.process0(entity);
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        return create(ex, handleBindingResult(ex.getBindingResult()), request);
    }

    @Override
    public ResponseEntity<Problem> handleAccessDenied(
        AccessDeniedException e, NativeWebRequest request) {
        return create(e, handleAccessDenied(), request);
    }

    @Override
    public ProblemBuilder prepare(
        final Throwable throwable, final StatusType status, final URI type) {
        return super.prepare0(throwable, status, type);
    }

    @Override
    protected boolean isCausalChainsEnabled0() {
        return isCausalChainsEnabled();
    }

    @Override
    protected ThrowableProblem toProblem0(final Throwable throwable) {
        return toProblem(throwable);
    }

    /**
     * 处理 BadRequestAlert 异常.
     *
     * @param ex      异常
     * @param request request请求
     * @return Problem 结果
     */
    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(
        BadRequestAlertException ex, NativeWebRequest request) {
        return create(ex, request,
            HeaderUtil.createFailureAlert(
                getApplicationName(),
                true,
                ex.getEntityName(),
                ex.getErrorKey(),
                ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(
        ConcurrencyFailureException ex, NativeWebRequest request) {
        return create(ex, handleConcurrencyFailure(ex), request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadCredentialsException(
        BadCredentialsException ex, NativeWebRequest request) {
        return create(ex, handleBadCredentialsException(ex), request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUnprocessableRequestAlertException(
        UnprocessableRequestAlertException ex, NativeWebRequest request) {
        return create(ex, handleUnprocessableRequestAlertException(ex), request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleNoSuchElementException(
        ResourceNotFoundAlertException ex, NativeWebRequest request) {
        return create(ex, handleNoSuchElementException(ex), request);
    }
}
