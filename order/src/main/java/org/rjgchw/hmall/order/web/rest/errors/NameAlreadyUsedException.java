package org.rjgchw.hmall.order.web.rest.errors;


import org.rjgchw.hmall.common.web.rest.error.UnprocessableRequestAlertException;
import org.rjgchw.hmall.common.web.rest.error.UnprocessableRequestErrorCode;

/**
 * @author Huangw
 * @date 2020-01-02 15:28
 */
public class NameAlreadyUsedException extends UnprocessableRequestAlertException {

    public NameAlreadyUsedException(String defaultMessage, String resource) {
        super(defaultMessage, resource, "name", UnprocessableRequestErrorCode.ALREADY_EXISTS);
    }
}
