package org.rjgchw.hmall.order.service.mapper;

import org.mapstruct.Mapper;
import org.rjgchw.hmall.common.mapper.EntityMapper;
import org.rjgchw.hmall.order.entity.ProductCategory;
import org.rjgchw.hmall.order.service.dto.ProductCategoryDTO;

/**
 * @author Huangw
 * @date 2020-04-01 16:14
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {

}
