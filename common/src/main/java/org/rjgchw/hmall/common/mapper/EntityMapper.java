package org.rjgchw.hmall.common.mapper;

import java.util.List;

/**
 * DTO 与 Entity 相互转换的 Mapper
 * @author Huangw
 * @date 2020-01-08 22:06
 */
public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);
}
