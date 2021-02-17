package org.rjgchw.hmall.order.service;

import org.rjgchw.hmall.order.entity.ProductCategory;
import org.rjgchw.hmall.order.repository.ProductCategoryRepository;
import org.rjgchw.hmall.order.service.dto.ProductCategoryDTO;
import org.rjgchw.hmall.order.service.error.CategoryHasChildException;
import org.rjgchw.hmall.order.service.mapper.ProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Huangw
 * @date 2020-04-01 16:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductCategoryService {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryService.class);

    private final ProductCategoryMapper productCategoryMapper;

    private final ProductCategoryRepository productCategoryRepository;
    private final org.rjgchw.hmall.order.service.ProductService productService;

    public ProductCategoryService(ProductCategoryMapper productCategoryMapper, ProductCategoryRepository productCategoryRepository, ProductService productService) {
        this.productCategoryMapper = productCategoryMapper;
        this.productCategoryRepository = productCategoryRepository;
        this.productService = productService;
    }

    /**
     * Create Information for ProductCategory
     * @param productCategoryDTO
     * @return
     */
    public ProductCategoryDTO create(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryMapper.toEntity(productCategoryDTO);
        productCategoryRepository.save(productCategory);
        log.debug("Created Information for ProductCategory: {}", productCategory);
        return productCategoryMapper.toDto(productCategory);
    }

    /**
     * Find ProductCategory By Id
     * @param id
     * @return
     */
    public Optional<ProductCategoryDTO> getById(Long id) {
        return productCategoryRepository.findById(id).map(productCategoryMapper::toDto);
    }

    public Optional<ProductCategoryDTO> update(Long id, ProductCategoryDTO productCategoryDTO) {
        return Optional.of(productCategoryRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(productCategory -> {
                productCategory.setName(productCategoryDTO.getName());
                productCategoryRepository.save(productCategory);
                log.debug("Changed Information for Product Category: {}", productCategory);
                return productCategory;
            }).map(productCategoryMapper::toDto);
    }

    public void delete(Long id) {
        if (productService.hasProductInCategory(id)) {
            throw new CategoryHasChildException("there are products under the category");
        }
        productCategoryRepository.findById(id).ifPresent(productCategoryRepository::delete);
    }
}
