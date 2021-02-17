package org.rjgchw.hmall.order.service;

import io.github.jhipster.service.QueryService;
import org.rjgchw.hmall.order.entity.ProductBrand;
import org.rjgchw.hmall.order.entity.ProductBrand_;
import org.rjgchw.hmall.order.repository.ProductBrandRepository;
import org.rjgchw.hmall.order.service.dto.ProductBrandCriteria;
import org.rjgchw.hmall.order.service.dto.ProductBrandDTO;
import org.rjgchw.hmall.order.service.mapper.ProductBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Huangw
 * @date 2020-03-08 17:13
 */
@Service
@Transactional(readOnly = true)
public class ProductBrandQueryService extends QueryService<ProductBrand> {

    private final Logger log = LoggerFactory.getLogger(ProductBrandQueryService.class);

    private final ProductBrandRepository productBrandRepository;
    private final ProductBrandMapper productBrandMapper;

    public ProductBrandQueryService(ProductBrandRepository productBrandRepository, ProductBrandMapper productBrandMapper) {
        this.productBrandRepository = productBrandRepository;
        this.productBrandMapper = productBrandMapper;
    }

    /**
     * Return a {@link Page} of {@link ProductBrandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    public Page<ProductBrandDTO> findByCriteria(ProductBrandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductBrand> specification = createSpecification(criteria);
        return productBrandRepository.findAll(specification, page)
            .map(productBrandMapper::toDto);
    }

    /**
     * Function to convert {@link ProductBrandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductBrand> createSpecification(ProductBrandCriteria criteria) {
        Specification<ProductBrand> specification = Specification.where((Specification<ProductBrand>) (root, query, criteriaBuilder) -> null);
        if (criteria != null) {
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductBrand_.name));
            }
        }
        return specification;
    }
}
