package org.rjgchw.hmall.storage.service.dto;

import java.io.Serializable;

/**
 * 库存扣减 DTO
 * @author Huangw
 * @date 2021-02-17 11:30
 */
public class StorageDeductDTO implements Serializable {

    private Long productId;

    private Integer productQuantity;

    private Boolean result;

    public StorageDeductDTO(){}

    public StorageDeductDTO(Long productId, Integer productQuantity, Boolean result) {
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.result = result;
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

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "StorageDeductDTO{" +
            "productId=" + productId +
            ", productQuantity=" + productQuantity +
            ", result=" + result +
            '}';
    }
}
