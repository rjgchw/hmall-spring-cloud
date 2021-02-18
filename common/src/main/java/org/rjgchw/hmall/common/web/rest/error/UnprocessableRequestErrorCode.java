package org.rjgchw.hmall.common.web.rest.error;

/**
 * @author Huangw
 * @date 2020-01-02 15:48
 */
public enum UnprocessableRequestErrorCode {
    /**
     * 缺失必要字段
     */
    MISSING_FIELD("missing_field"),
    /**
     * 资源未找到
     */
    MISSING("missing"),
    /**
     * 字段非法
     */
    INVALID("invalid"),
    /**
     * 字段值已存在
     */
    ALREADY_EXISTS("already_exists");

    private String code;

    UnprocessableRequestErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
