package org.rjgchw.hmall.storage.service.dto;

//import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 库存 DTO
 * @author Huangw
 * @date 2021-02-17 11:30
 */
public class StorageDTO {

//    @Schema(example = "1")
    private Long id;

//    @Schema(description = "商品id", required = true, example = "1")
    @NotNull
    @Min(0)
    private Long productId;

//    @Schema(description = "库存", example = "100")
    @Min(0)
    private Integer stock;

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StorageDTO stockDTO = (StorageDTO) o;

        return id.equals(stockDTO.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
