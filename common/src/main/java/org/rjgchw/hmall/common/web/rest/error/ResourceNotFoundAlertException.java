package org.rjgchw.hmall.common.web.rest.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * ResourceNotFoundAlertException
 * @author Huangw
 * @date 2020-04-01 17:42
 */
public class ResourceNotFoundAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundAlertException(String defaultMessage) {
        super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, defaultMessage, Status.NOT_FOUND, null, null, null);
    }
}
