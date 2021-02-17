package org.rjgchw.hmall.order.service.mapper;

import org.mapstruct.Mapper;
import org.rjgchw.hmall.common.mapper.EntityMapper;
import org.rjgchw.hmall.order.entity.Order;
import org.rjgchw.hmall.order.service.dto.OrderDTO;

/**
 * Order Mapper
 * @author Huangw
 * @date 2020-04-05 20:14
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

}
