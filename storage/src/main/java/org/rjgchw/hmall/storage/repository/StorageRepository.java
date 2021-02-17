package org.rjgchw.hmall.storage.repository;

import org.rjgchw.hmall.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 *
 * @author Huangw
 * @date 2020-09-05 15:18
 */
public interface StorageRepository extends JpaRepository<Storage, Long>, JpaSpecificationExecutor<Storage> {

    /**
     * 查询库存信息
     * @param productId 产品 id
     * @return
     */
    Optional<Storage> findByProductId(Long productId);

    /**
     * 锁库存
     *
     * @param productId       产品id
     * @param sourceStock     产品原库存
     * @param productQuantity 产品锁定数量
     * @return 影响条数
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE h_stock SET stock = stock - :productQuantity WHERE product_id = :productId and stock = :sourceStock", nativeQuery = true)
    int lockProduct(@Param("productId") Long productId, @Param("sourceStock") Integer sourceStock, @Param("productQuantity") Integer productQuantity);
}
