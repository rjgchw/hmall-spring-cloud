package org.rjgchw.hmall.common.web.rest.error;

import java.net.URI;

/**
 * ErrorConstants
 * @author Huangw
 * @date 2019-12-01 22:24
 */
public final class ErrorConstants {

    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
    public static final URI BAD_REQUEST_TYPE = URI.create(PROBLEM_BASE_URL + "/bad-request");
    public static final URI SERVER_ERROR_TYPE = URI.create(PROBLEM_BASE_URL + "/system-error");

    public static final String ERR_VALIDATION = "error.validation";
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    private ErrorConstants() {
    }
}
