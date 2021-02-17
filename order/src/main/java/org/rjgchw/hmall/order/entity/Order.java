package org.rjgchw.hmall.order.entity;

import org.rjgchw.hmall.common.entity.AbstractAuditingEntity;
import org.rjgchw.hmall.order.service.enums.OrderStatusEnum;
import org.rjgchw.hmall.order.service.enums.PayTypeEnum;
import org.rjgchw.hmall.order.service.enums.SourceTypeEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Order Entity
 * @author Huangw
 * @date 2020-02-01 18:32
 */
@Entity
@Table(name = "h_order")
public class Order extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, unique = true, nullable = false)
    private String orderSn;

    @Column(length = 64, nullable = false)
    private String memberUsername;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private Long receiverId;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private SourceTypeEnum sourceType;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PayTypeEnum payType;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> items;

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

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
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

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return orderSn.equals(order.orderSn);
    }

    public SourceTypeEnum getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeEnum sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderSn);
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderSn='" + orderSn + '\'' +
            ", memberUsername='" + memberUsername + '\'' +
            ", totalAmount=" + totalAmount +
            ", receiverId='" + receiverId + '\'' +
            ", sourceType=" + sourceType +
            ", payType=" + payType +
            ", status=" + status +
            '}';
    }
}
