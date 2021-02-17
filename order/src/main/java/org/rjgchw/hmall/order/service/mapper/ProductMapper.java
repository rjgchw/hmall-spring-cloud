package org.rjgchw.hmall.order.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.rjgchw.hmall.common.mapper.EntityMapper;
import org.rjgchw.hmall.order.entity.Product;
import org.rjgchw.hmall.order.service.dto.ProductDTO;

/**
 * @author Huangw
 * @date 2020-04-01 16:15
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    /**
     * mapper entity to dto
     * @param entity
     * @return
     */
    @Mappings({
        @Mapping(source = "category.id", target = "categoryId"),
    })
    @Override
    ProductDTO toDto(Product entity);
}
