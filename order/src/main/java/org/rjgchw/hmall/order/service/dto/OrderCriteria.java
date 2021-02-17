package org.rjgchw.hmall.order.service.dto;

/**
 * @author Huangw
 * @date 2020-03-22 15:08
 */
public class OrderCriteria {

    private OrderStatusFilter status;

    public OrderStatusFilter getStatus() {
        return status;
    }

    public void setStatus(OrderStatusFilter status) {
        this.status = status;
    }
}
