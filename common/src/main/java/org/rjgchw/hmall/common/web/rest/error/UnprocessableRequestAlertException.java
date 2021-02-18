package org.rjgchw.hmall.common.web.rest.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

/**
 * 不可处理请求异常 表示请求不符合业务规则
 * @author Huangw
 * @date 2019-11-19 23:06
 */
public class UnprocessableRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final FieldErrorVO field;

    public UnprocessableRequestAlertException(String defaultMessage, String resource, String field, UnprocessableRequestErrorCode code) {
        this(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, defaultMessage, new FieldErrorVO(resource, field, code));
    }

    public UnprocessableRequestAlertException(String defaultMessage, FieldErrorVO field) {
        this(ErrorConstants.CONSTRAINT_VIOLATION_TYPE, defaultMessage, field);
    }

    private UnprocessableRequestAlertException(URI type, String defaultMessage, FieldErrorVO field) {
        super(type, defaultMessage, Status.UNPROCESSABLE_ENTITY, null, null, null);
        this.field = field;
    }

    public FieldErrorVO getField() {
        return field;
    }
}
