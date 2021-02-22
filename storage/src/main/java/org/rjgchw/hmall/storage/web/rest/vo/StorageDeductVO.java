package org.rjgchw.hmall.storage.web.rest.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 库存扣减 DTO
 * @author Huangw
 * @date 2021-02-17 11:30
 */
public class StorageDeductVO implements Serializable {

    @Schema(description = "商品id", required = true, example = "1")
    @NotNull
    @Min(0)
    private Long productId;

    @Schema(description = "数量", required = true, example = "1")
    @Min(0)
    private Integer productQuantity;

    public StorageDeductVO(){}

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
