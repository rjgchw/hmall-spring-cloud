package org.rjgchw.hmall.order.service.mapper;

import org.mapstruct.Mapper;
import org.rjgchw.hmall.common.mapper.EntityMapper;
import org.rjgchw.hmall.order.entity.ProductBrand;
import org.rjgchw.hmall.order.service.dto.ProductBrandDTO;

/**
 *
 * @author Huangw
 * @date 2020-03-08 16:59
 */
@Mapper(componentModel = "spring")
public interface ProductBrandMapper extends EntityMapper<ProductBrandDTO, ProductBrand> {

}
