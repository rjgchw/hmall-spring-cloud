package org.rjgchw.hmall.common.web.rest.error;

/**
 * @author Huangw
 * @date 2020-01-02 16:00
 */
public class UnknownErrorCodeException extends RuntimeException{

    public UnknownErrorCodeException() {
        super("Unknown Unprocessable request error code");
    }
}
