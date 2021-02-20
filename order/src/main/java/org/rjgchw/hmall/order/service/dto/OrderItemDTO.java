package org.rjgchw.hmall.order.service.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Huangw
 * @date 2020-04-05 20:18
 */
public class OrderItemDTO {

    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "产品 id", example = "1")
    @NotNull
    @Min(0)
    private Long productId;

    @Schema(description = "产品单价", example = "3999.00")
    private BigDecimal productPrice;

    @Schema(description = "产品数量", example = "1")
    private Integer productQuantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItemDTO that = (OrderItemDTO) o;

        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + id +
            ", productId=" + productId +
            ", productPrice=" + productPrice +
            ", productQuantity=" + productQuantity +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
