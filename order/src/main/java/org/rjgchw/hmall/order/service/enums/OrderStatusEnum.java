package org.rjgchw.hmall.order.service.enums;

/**
 * Order Status
 * @author Huangw
 * @date 2020-02-01 20:24
 */
public enum OrderStatusEnum {
    /**
     * 待支付
     */
    PENDING_PAYMENT,
    /**
     * 待发货
     */
    CONFIRMED,
    /**
     * 已发货
     */
    SHIPPED,
    /**
     * 已完成
     */
    FINISHED,
    /**
     * 已关闭
     */
    CLOSED,
    /**
     * 无效订单
     */
    INVALID
    ;
}
