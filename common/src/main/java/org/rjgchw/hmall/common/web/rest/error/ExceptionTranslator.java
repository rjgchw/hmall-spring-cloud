package org.rjgchw.hmall.common.web.rest.error;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.zalando.problem.Status.UNPROCESSABLE_ENTITY;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 * @author huangwei
 */
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String FIELD_ERRORS_KEY = "errors";
    private static final String MESSAGE_KEY = "message";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

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

    @Override
    public StatusType defaultConstraintViolationStatus() {
        return UNPROCESSABLE_ENTITY;
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
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

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVO> fieldErrors = result.getFieldErrors().stream()
            .map(f -> new FieldErrorVO(f.getObjectName(), f.getField(), ErrorCodeConverter.getErrorCode(f.getCode())))
            .collect(Collectors.toList());

        Problem problem = Problem.builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Validation Failed")
            .withStatus(defaultConstraintViolationStatus())
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUnprocessableRequestAlertException(UnprocessableRequestAlertException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle(ex.getTitle())
            .withStatus(defaultConstraintViolationStatus())
            .with(FIELD_ERRORS_KEY, Collections.singletonList(ex.getField()))
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleNoSuchElementException(ResourceNotFoundAlertException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withType(ex.getType())
            .withStatus(ex.getStatus())
            .withTitle(ex.getTitle())
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withStatus(Status.CONFLICT)
            .withTitle("Concurrency Failure")
            .with(MESSAGE_KEY, ex.getMessage() != null ? ex.getMessage() : "Concurrency Failure")
            .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadCredentialsException(BadCredentialsException ex, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withStatus(Status.UNAUTHORIZED)
            .withTitle("Unauthorized")
            .with(MESSAGE_KEY, ex.getMessage() != null ? ex.getMessage() : "Unauthorized")
            .build();
        return create(ex, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleAccessDenied(AccessDeniedException e, NativeWebRequest request) {
        Problem problem = Problem.builder()
            .withStatus(Status.FORBIDDEN)
            .withTitle("Access Forbidden")
            .build();
        return create(e, problem, request);
    }
}
