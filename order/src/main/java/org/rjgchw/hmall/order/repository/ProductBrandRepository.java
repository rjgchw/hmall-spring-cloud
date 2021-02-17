package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 *
 * @author Huangw
 * @date 2020-03-08 11:41
 */
public interface ProductBrandRepository extends JpaRepository<ProductBrand, Long>, JpaSpecificationExecutor<ProductBrand> {

    /**
     * find entity by name
     * @param name product brand name
     * @return
     */
    Optional<ProductBrand> findOneByName(String name);

}
