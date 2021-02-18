package org.rjgchw.hmall.common.web.rest.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * 请求内容体格式错误
 * @author Huangw
 * @date 2019-11-19 23:06
 */
public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(String defaultMessage) {
        super(ErrorConstants.BAD_REQUEST_TYPE, defaultMessage, Status.BAD_REQUEST, null, null, null);
    }
}
