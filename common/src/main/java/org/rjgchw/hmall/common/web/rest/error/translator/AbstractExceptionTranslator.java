package org.rjgchw.hmall.common.web.rest.error.translator;

import io.github.jhipster.config.JHipsterConstants;
import org.rjgchw.hmall.common.web.rest.error.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.zalando.problem.*;
import org.zalando.problem.violations.ConstraintViolationProblem;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 * @author huangwei
 */
public abstract class AbstractExceptionTranslator {

    protected static final String FIELD_ERRORS_KEY = "errors";
    protected static final String MESSAGE_KEY = "message";

    protected final Environment env;

    public AbstractExceptionTranslator(Environment env) {
        this.env = env;
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    protected ResponseEntity<Problem> process0(ResponseEntity<Problem> entity) {
        if (entity == null) {
            return null;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        ProblemBuilder builder = Problem.builder()
            .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
            .withStatus(problem.getStatus())
            .withTitle(problem.getTitle());

        problem.getParameters().forEach(builder::with);
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
    }

    protected Problem handleBindingResult(BindingResult result) {
        List<FieldErrorVO> fieldErrors = result.getFieldErrors().stream()
            .map(f -> new FieldErrorVO(
                f.getObjectName().replaceFirst("DTO$", ""),
                f.getField(),
                ErrorCodeConverter.getErrorCode(f.getCode())))
            .collect(Collectors.toList());

        return Problem.builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Validation Failed")
            .withStatus(Status.UNPROCESSABLE_ENTITY)
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
    }

    protected Problem handleConcurrencyFailure(ConcurrencyFailureException ex) {
        return Problem.builder()
            .withStatus(Status.CONFLICT)
            .withTitle("Concurrency Failure")
            .with(MESSAGE_KEY, ex.getMessage() != null ? ex.getMessage() : "Concurrency Failure")
            .build();
    }

    protected Problem handleAccessDenied() {
        return Problem.builder()
            .withStatus(Status.FORBIDDEN)
            .withTitle("Access Forbidden")
            .build();
    }

    protected ProblemBuilder prepare0(final Throwable throwable, final StatusType status, final URI type) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return Problem.builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unable to convert http message")
                    .withCause(Optional.ofNullable(throwable.getCause())
                        .filter(cause -> isCausalChainsEnabled0())
                        .map(this::toProblem0)
                        .orElse(null));
            }
            if (throwable instanceof DataAccessException) {
                return Problem.builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Failure during data access")
                    .withCause(Optional.ofNullable(throwable.getCause())
                        .filter(cause -> isCausalChainsEnabled0())
                        .map(this::toProblem0)
                        .orElse(null));
            }
            if (containsPackageName(throwable.getMessage())) {
                return Problem.builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unexpected runtime exception")
                    .withCause(Optional.ofNullable(throwable.getCause())
                        .filter(cause -> isCausalChainsEnabled0())
                        .map(this::toProblem0)
                        .orElse(null));
            }
        }

        return Problem.builder()
            .withType(type)
            .withTitle(status.getReasonPhrase())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withCause(Optional.ofNullable(throwable.getCause())
                .filter(cause -> isCausalChainsEnabled0())
                .map(this::toProblem0)
                .orElse(null));
    }

    protected abstract boolean containsPackageName(String message);

    protected StatusType defaultConstraintViolationStatus0() {
        return Status.UNPROCESSABLE_ENTITY;
    }

    protected abstract boolean isCausalChainsEnabled0();

    protected abstract ThrowableProblem toProblem0(final Throwable throwable);

    protected abstract String getApplicationName();

    protected Problem handleBadCredentialsException(BadCredentialsException ex) {
        return Problem.builder()
            .withStatus(Status.UNAUTHORIZED)
            .withTitle("Unauthorized")
            .with(MESSAGE_KEY, ex.getMessage() != null ? ex.getMessage() : "Unauthorized")
            .build();
    }

    protected Problem handleUnprocessableRequestAlertException(UnprocessableRequestAlertException ex) {
        return Problem.builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle(ex.getTitle())
            .withStatus(defaultConstraintViolationStatus0())
            .with(FIELD_ERRORS_KEY, Collections.singletonList(ex.getField()))
            .build();
    }

    protected Problem handleNoSuchElementException(ResourceNotFoundAlertException ex) {
        return Problem.builder()
            .withType(ex.getType())
            .withStatus(ex.getStatus())
            .withTitle(ex.getTitle())
            .build();
    }
}
