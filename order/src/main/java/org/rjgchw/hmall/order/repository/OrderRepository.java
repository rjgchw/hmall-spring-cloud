package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Order Repository
 * @author Huangw
 * @date 2020-02-03 22:17
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
