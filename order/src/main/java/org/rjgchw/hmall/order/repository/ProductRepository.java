package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author Huangw
 * @date 2020-01-16 17:46
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * find entity by name
     * @param name product name
     * @return
     */
    Optional<Product> findOneByName(String name);

    /**
     * has product in category id
     * @param categoryId
     * @return
     */
    boolean existsByCategoryId(Long categoryId);
}
