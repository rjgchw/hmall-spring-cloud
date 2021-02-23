package org.rjgchw.hmall.common.mapper;

import java.util.List;

/**
 * DTO 与 Entity 相互转换的 Mapper
 *
 * @author Huangw
 * @date 2020-01-08 22:06
 */
public interface EntityMapper<D, E> {

    /**
     * Entity to DTO
     *
     * @param dto
     * @return
     */
    E toEntity(D dto);

    /**
     * DTO to Entity
     *
     * @param entity
     * @return
     */
    D toDto(E entity);

    /**
     * DTO to Entity
     *
     * @param dtoList
     * @return
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity to DTO
     *
     * @param entityList
     * @return
     */
    List<D> toDto(List<E> entityList);
}
