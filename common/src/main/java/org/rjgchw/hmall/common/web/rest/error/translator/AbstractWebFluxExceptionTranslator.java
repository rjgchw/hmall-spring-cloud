package org.rjgchw.hmall.common.web.rest.error.translator;

import io.github.jhipster.web.util.HeaderUtil;
import org.rjgchw.hmall.common.web.rest.error.BadRequestAlertException;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 *
 * @author huangwei
 */
public abstract class AbstractWebFluxExceptionTranslator extends AbstractExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    public AbstractWebFluxExceptionTranslator(Environment env) {
        super(env);
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public Mono<ResponseEntity<Problem>> process(@Nullable ResponseEntity<Problem> entity, ServerWebExchange request) {
        if (entity == null) {
            return Mono.empty();
        }
        return Mono.just(super.process0(entity, request.getRequest().getPath().value()));
    }

    @Override
    public Mono<ResponseEntity<Problem>> handleBindingResult(WebExchangeBindException ex, @Nonnull ServerWebExchange request) {
        return create(ex, handleBindingResult(ex.getBindingResult()), request);
    }

    @ExceptionHandler
    public Mono<ResponseEntity<Problem>> handleBadRequestAlertException(BadRequestAlertException ex, ServerWebExchange request) {
        return create(ex, request, HeaderUtil.createFailureAlert(getApplicationName(), false, ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
    }

    @ExceptionHandler
    public Mono<ResponseEntity<Problem>> handleConcurrencyFailure(ConcurrencyFailureException ex, ServerWebExchange request) {
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
