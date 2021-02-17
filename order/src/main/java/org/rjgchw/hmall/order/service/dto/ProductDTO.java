package org.rjgchw.hmall.order.service.dto;

import org.rjgchw.hmall.order.service.enums.ProductStatusEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * 商品 DTO
 * @author Huangw
 * @date 2020-04-01 10:50
 */
public class ProductDTO {

//    @Schema(example = "1")
    private Long id;

//    @Schema(description = "商品名称", required = true, example = "小米9")
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

//    @Schema(description = "商品价格", required = true, example = "3999.00")
    @Min(0)
    private BigDecimal price;

//    @Schema(description = "商品描述", example = "小米9是最新的小米旗舰机")
    @Size(max = 254)
    private String description;

//    @Schema(description = "商品图片url", example = "https://image.com/xiaomi.jpg")
    @Size(max = 254)
    private String imgUrl;

//    @Schema(description = "商品状态, UP: 上架, DOWN: 下架", example = "UP")
    @NotNull
    private ProductStatusEnum status;

//    @Schema(description = "商品类别id", example = "1")
    @NotNull
    private Long categoryId;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO that = (ProductDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", description='" + description + '\'' +
            ", imgUrl='" + imgUrl + '\'' +
            ", status=" + status +
            ", categoryId=" + categoryId +
            '}';
    }
}
