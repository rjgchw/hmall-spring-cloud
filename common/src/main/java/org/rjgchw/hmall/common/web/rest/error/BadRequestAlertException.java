package org.rjgchw.hmall.common.web.rest.error;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * 请求内容体格式错误.
 *
 * @author Huangw
 * @date 2019-11-19 23:06
 */
public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    /**
     * 构造函数.
     *
     * @param type           类型
     * @param defaultMessage 默认消息
     * @param entityName     实体名称
     * @param errorKey       错误的 key
     */
    public BadRequestAlertException(
        URI type, String defaultMessage, String entityName, String errorKey) {
        super(
            type,
            defaultMessage,
            Status.BAD_REQUEST,
            null,
            null,
            null,
            getAlertParameters(entityName, errorKey)
        );
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap<>(16);
        parameters.put("message", "error." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
