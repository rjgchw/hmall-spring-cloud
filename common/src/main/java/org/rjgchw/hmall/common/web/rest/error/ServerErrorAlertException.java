package org.rjgchw.hmall.common.web.rest.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * 系统错误，需要联系管理员
 * @author Huangw
 * @date 2019-11-19 23:06
 */
public class ServerErrorAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private ServerErrorAlertException(String defaultMessage) {
        super(ErrorConstants.SERVER_ERROR_TYPE, defaultMessage, Status.UNPROCESSABLE_ENTITY, null, null, null);
    }
}
