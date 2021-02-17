package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 锁产品
     * @param productId 产品id
     * @param sourceStock 产品原库存
     * @param productQuantity 产品锁定数量
     * @return 影响条数
     */
    @Modifying
    @Query(value = "UPDATE h_product SET stock = stock - :productQuantity WHERE id = :productId and stock = :sourceStock", nativeQuery = true)
    int lockProduct(@Param("productId") Long productId, @Param("sourceStock") Integer sourceStock, @Param("productQuantity") Integer productQuantity);
}
