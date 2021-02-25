package org.rjgchw.hmall.common.web.rest.error.translator;

import java.net.URI;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.rjgchw.hmall.common.web.rest.error.BadRequestAlertException;
import org.rjgchw.hmall.common.web.rest.error.ResourceNotFoundAlertException;
import org.rjgchw.hmall.common.web.rest.error.UnprocessableRequestAlertException;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.spring.webflux.advice.security.SecurityAdviceTrait;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures. The
 * error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 *
 * @author huangwei
 */
public abstract class AbstractWebFluxExceptionTranslator
    extends AbstractExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    protected AbstractWebFluxExceptionTranslator(Environment env) {
        super(env);
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public Mono<ResponseEntity<Problem>> process(
        @Nullable ResponseEntity<Problem> entity, ServerWebExchange request) {
        if (entity == null) {
            return Mono.empty();
        }
        return Mono.just(super.process0(entity));
    }

    @Override
    public Mono<ResponseEntity<Problem>> handleBindingResult(
        WebExchangeBindException ex, @Nonnull ServerWebExchange request) {
        return create(ex, handleBindingResult(ex.getBindingResult()), request);
    }

    @Override
    public Mono<ResponseEntity<Problem>> handleAccessDenied(
        AccessDeniedException e, ServerWebExchange request) {
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
    public Mono<ResponseEntity<Problem>> handleBadRequestAlertException(
        BadRequestAlertException ex, ServerWebExchange request) {
        return create(ex, request,
            HeaderUtil.createFailureAlert(
                getApplicationName(),
                false,
                ex.getEntityName(),
                ex.getErrorKey(),
                ex.getMessage()));
    }

    @ExceptionHandler
    public Mono<ResponseEntity<Problem>> handleConcurrencyFailure(
        ConcurrencyFailureException ex, ServerWebExchange request) {
        return create(ex, handleConcurrencyFailure(ex), request);
    }

    @ExceptionHandler
    public Mono<ResponseEntity<Problem>> handleBadCredentialsException(
        BadCredentialsException ex, ServerWebExchange request) {
        return create(ex, handleBadCredentialsException(ex), request);
    }

    @ExceptionHandler
    public Mono<ResponseEntity<Problem>> handleUnprocessableRequestAlertException(
        UnprocessableRequestAlertException ex, ServerWebExchange request) {
        return create(ex, handleUnprocessableRequestAlertException(ex), request);
    }

    @ExceptionHandler
    public Mono<ResponseEntity<Problem>> handleNoSuchElementException(
        ResourceNotFoundAlertException ex, ServerWebExchange request) {
        return create(ex, handleNoSuchElementException(ex), request);
    }
}
