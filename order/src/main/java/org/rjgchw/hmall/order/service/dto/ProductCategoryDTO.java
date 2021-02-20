package org.rjgchw.hmall.order.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Huangw
 * @date 2020-04-01 16:15
 */
public class ProductCategoryDTO {

    @Schema(description = "商品类别id", example = "1")
    private Long id;

    @Schema(description = "商品类别名称", required = true, example = "食品")
    @NotNull
    @Size(max = 64)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductCategoryDTO that = (ProductCategoryDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
