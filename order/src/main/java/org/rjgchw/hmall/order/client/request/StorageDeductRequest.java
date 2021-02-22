package org.rjgchw.hmall.order.client.request;

import java.io.Serializable;

/**
 * 库存扣减 Request
 * @author Huangw
 * @date 2021-02-17 11:30
 */
public class StorageDeductRequest implements Serializable {

    private Long productId;

    private Integer productQuantity;

    public StorageDeductRequest(Long productId, Integer productQuantity) {
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
