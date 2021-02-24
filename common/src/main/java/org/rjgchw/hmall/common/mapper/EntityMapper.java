package org.rjgchw.hmall.common.mapper;

import java.util.List;

/**
 * DTO 与 Entity 相互转换的 Mapper.
 *
 * @author Huangw
 * @date 2020-01-08 22:06
 */
public interface EntityMapper<D, E> {

    /**
     * Entity to DTO.
     *
     * @param dto dto
     * @return entity
     */
    E toEntity(D dto);

    /**
     * DTO to Entity.
     *
     * @param dtoList dto list
     * @return entity list
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * DTO to Entity.
     *
     * @param entity entity
     * @return dto
     */
    D toDto(E entity);

    /**
     * Entity to DTO.
     *
     * @param entityList entity list
     * @return dto list
     */
    List<D> toDto(List<E> entityList);
}
