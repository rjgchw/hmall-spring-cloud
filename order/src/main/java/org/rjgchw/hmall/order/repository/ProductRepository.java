package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Huangw
 * @date 2020-01-16 17:46
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    String PRODUCT_BY_NAME = "productByName";

    /**
     * find entity by name
     *     @EntityGraph(attributePaths = "authorities")
     * @param name product name
     * @return
     */
    @Cacheable(cacheNames = PRODUCT_BY_NAME)
    Optional<Product> findOneByName(String name);

    /**
     * has product in category id
     * @param categoryId
     * @return
     */
    boolean existsByCategoryId(Long categoryId);
}
