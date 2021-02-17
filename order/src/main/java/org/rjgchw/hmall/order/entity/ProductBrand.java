package org.rjgchw.hmall.order.entity;

import org.rjgchw.hmall.common.entity.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品品牌
 * @author Huangw
 * @date 2020-03-07 22:23
 */
@Entity
@Table(name = "h_product_brand")
public class ProductBrand extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, unique = true, nullable = false)
    private String name;

    @Column(length = 1)
    private String firstLetter;

    private Integer sort;

    private Boolean isFactory;

    private Boolean isShow;

    private Integer productCount;

    private Integer productCommentCount;

    @Column(length = 512)
    private String logo;

    @Column(length = 512)
    private String bigPic;

    @Column(length = 512)
    private String brandStory;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductBrand that = (ProductBrand) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ProductBrand{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", firstLetter='" + firstLetter + '\'' +
            ", sort=" + sort +
            ", isFactory=" + isFactory +
            ", isShow=" + isShow +
            ", productCount=" + productCount +
            ", productCommentCount=" + productCommentCount +
            ", logo='" + logo + '\'' +
            ", bigPic='" + bigPic + '\'' +
            ", brandStory='" + brandStory + '\'' +
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

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Integer getProductCommentCount() {
        return productCommentCount;
    }

    public void setProductCommentCount(Integer productCommentCount) {
        this.productCommentCount = productCommentCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getBrandStory() {
        return brandStory;
    }

    public void setBrandStory(String brandStory) {
        this.brandStory = brandStory;
    }
}
