package org.rjgchw.hmall.common.web.rest.error;

import java.io.Serializable;

/**
 * field error vo.
 *
 * @author huangwei
 */
public class FieldErrorVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String resource;

    private final String field;

    private final UnprocessableRequestErrorCode code;

    /**
     * constructor.
     *
     * @param resource resource
     * @param field    field
     * @param code     code
     */
    public FieldErrorVO(String resource, String field, UnprocessableRequestErrorCode code) {
        this.resource = resource;
        this.field = field;
        this.code = code;
    }

    public String getResource() {
        return resource;
    }

    public String getField() {
        return field;
    }

    @Override
    public String toString() {
        return "FieldErrorVO{"
                + "resource='" + resource + '\''
                + ", field='" + field + '\''
                + ", code='" + code + '\''
                + '}';
    }

    public String getCode() {
        return code.toString();
    }
}
