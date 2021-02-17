package org.rjgchw.hmall.order.service.dto;

import org.rjgchw.hmall.order.service.enums.OrderStatusEnum;
import org.rjgchw.hmall.order.service.enums.PayTypeEnum;
import org.rjgchw.hmall.order.service.enums.SourceTypeEnum;

import java.math.BigDecimal;

/**
 * 订单 DTO
 * @author Huangw
 * @date 2020-04-01 13:30
 */
public class OrderDTO {

//    @Schema(description = "订单 id", example = "1")
    private Long id;

//    @Schema(description = "订单编号", example = "202003040101000001")
    private String orderSn;

//    @Schema(description = "会员", example = "user")
    private String memberUsername;

//    @Schema(description = "订单总金额", example = "3000.23")
    private BigDecimal totalAmount;

//    @Schema(description = "收货人 id", example = "1")
    private Long receiverId;

//    @Schema(description = "订单来源", example = "PC")
    private SourceTypeEnum sourceType;

//    @Schema(description = "支付方式", example = "ALIPAY")
    private PayTypeEnum payType;

//    @Schema(description = "订单状态", example = "CONFIRMED")
    private OrderStatusEnum status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;

        return orderSn.equals(orderDTO.orderSn);
    }

    @Override
    public int hashCode() {
        return orderSn.hashCode();
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + id +
            ", orderSn='" + orderSn + '\'' +
            ", memberUsername='" + memberUsername + '\'' +
            ", totalAmount=" + totalAmount +
            ", receiverId=" + receiverId +
            ", sourceType=" + sourceType +
            ", payType=" + payType +
            ", status=" + status +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public SourceTypeEnum getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeEnum sourceType) {
        this.sourceType = sourceType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

}
