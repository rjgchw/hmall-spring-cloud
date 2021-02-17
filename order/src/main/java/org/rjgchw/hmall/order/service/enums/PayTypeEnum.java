package org.rjgchw.hmall.order.service.enums;

/**
 * Order Pay Status
 * @author Huangw
 * @date 2020-02-01 20:24
 */
public enum PayTypeEnum {
    /**
     * 未支付
     */
    UNPAID(0),
    /**
     * 支付宝
     */
    ALIPAY(1),
    /**
     * 微信支付
     */
    WECHAT_PAY(2)
    ;

    private Integer value;

    PayTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
