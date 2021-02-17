package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author Huangw
 * @date 2020-01-16 17:46
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, JpaSpecificationExecutor<ProductCategory> {

    /**
     * find entity by name
     * @param name product category name
     * @return
     */
    Optional<ProductCategory> findOneByName(String name);

}
