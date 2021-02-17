package org.rjgchw.hmall.order.service.enums;

/**
 * @author Huangw
 * @date 2020-02-01 20:24
 */
public enum SourceTypeEnum {
    /**
     * PC 端
     */
    PC(1),
    /**
     * APP 端
     */
    APP(2),
    ;

    private Integer value;

    SourceTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
