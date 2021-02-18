package org.rjgchw.hmall.storage.entity;

import org.rjgchw.hmall.common.entity.AbstractAuditingEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * product storage.
 *
 * @author huangwei
 */
@Entity
@Table(name = "h_storage")
public class Storage extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer storage = 0;

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

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Storage storage = (Storage) o;

        return id.equals(storage.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
