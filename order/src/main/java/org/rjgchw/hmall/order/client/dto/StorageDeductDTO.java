package org.rjgchw.hmall.order.client.dto;

import java.io.Serializable;

/**
 * 库存扣减 DTO
 * @author Huangw
 * @date 2021-02-17 11:30
 */
public class StorageDeductDTO implements Serializable {

    private Long productId;

    private Integer productQuantity;

    public StorageDeductDTO(Long productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
