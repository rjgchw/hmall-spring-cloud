package org.rjgchw.hmall.order.web.rest.vo;

//import io.swagger.v3.oas.annotations.media.Schema;
import org.rjgchw.hmall.order.service.dto.OrderItemDTO;
import org.rjgchw.hmall.order.service.enums.PayTypeEnum;
import org.rjgchw.hmall.order.service.enums.SourceTypeEnum;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Huangw
 * @date 2020-04-07 15:23
 */
public class OrderVO {

//    @Schema(description = "订单来源", example = "PC")
    @NotNull
    private SourceTypeEnum sourceType;

//    @Schema(description = "支付方式", example = "ALIPAY")
    @NotNull
    private PayTypeEnum payType;

//    @Schema(description = "收货人 id", example = "1")
    @NotNull
    private Long receiverId;

//    @Schema(description = "商品列表")
    @NotNull
    @Valid
    private Set<OrderItemDTO> items;

    @Override
    public String toString() {
        return "OrderVO{" +
            "sourceType=" + sourceType +
            ", payType=" + payType +
            ", receiverId=" + receiverId +
            ", items=" + items +
            '}';
    }

    public SourceTypeEnum getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeEnum sourceType) {
        this.sourceType = sourceType;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Set<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDTO> items) {
        this.items = items;
    }
}
