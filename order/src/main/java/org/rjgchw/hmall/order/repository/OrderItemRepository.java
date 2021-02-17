package org.rjgchw.hmall.order.repository;

import org.rjgchw.hmall.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Order Item Repository
 * @author Huangw
 * @date 2020-02-03 22:17
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {

}
