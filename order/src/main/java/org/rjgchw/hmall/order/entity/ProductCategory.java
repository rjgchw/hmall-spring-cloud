package org.rjgchw.hmall.order.entity;

import org.rjgchw.hmall.common.entity.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A user.
 * @author huangwei
 */
@Entity
@Table(name = "h_product_category")
public class ProductCategory extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, unique = true, nullable = false)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
