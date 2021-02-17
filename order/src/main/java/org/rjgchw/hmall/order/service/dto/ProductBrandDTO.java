package org.rjgchw.hmall.order.service.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Huangw
 * @date 2020-03-22 15:06
 */
public class ProductBrandDTO {

//    @Schema(example = "1")
    private Long id;

//    @Schema(description = "品牌名称", required = true, example = "小米")
    @NotNull
    @Size(min = 1, max = 64)
    private String name;

//    @Schema(description = "品牌首字母", example = "M")
    @Size(min = 1, max = 1)
    private String firstLetter;

//    @Schema(description = "排序", example = "500")
    @Max(10000)
    @Min(0)
    private Integer sort;

//    @Schema(description = "是否品牌制造商", required = true, defaultValue = "false", example = "true")
    @NotNull
    private Boolean isFactory;

//    @Schema(description = "是否显示", required = true, defaultValue = "false", example = "true")
    @NotNull
    private Boolean isShow;

//    @Schema(description = "产品logo", required = true, example = "https://localhost/oss/images/20180518/5a912944N474af.png")
    @NotNull
    @Size(min = 1, max = 512)
    private String logo;

//    @Schema(description = "品牌故事", example = "Nike 成立于...")
    @Size(min = 1, max = 2000)
    private String brandStory;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductBrandDTO that = (ProductBrandDTO) o;

        return id.equals(that.id);
    }

    @Override
    public String toString() {
        return "ProductBrandDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", firstLetter='" + firstLetter + '\'' +
            ", sort=" + sort +
            ", isFactory=" + isFactory +
            ", isShow=" + isShow +
            ", logo='" + logo + '\'' +
            ", brandStory='" + brandStory + '\'' +
            '}';
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getFactory() {
        return isFactory;
    }

    public void setFactory(Boolean factory) {
        isFactory = factory;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBrandStory() {
        return brandStory;
    }

    public void setBrandStory(String brandStory) {
        this.brandStory = brandStory;
    }
}
