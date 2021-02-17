package org.rjgchw.hmall.order.service;

import org.rjgchw.hmall.order.entity.Product;
import org.rjgchw.hmall.order.entity.ProductCategory;
import org.rjgchw.hmall.order.repository.ProductCategoryRepository;
import org.rjgchw.hmall.order.repository.ProductRepository;
import org.rjgchw.hmall.order.service.dto.ProductDTO;
import org.rjgchw.hmall.order.service.error.ProductCategoryResourceException;
import org.rjgchw.hmall.order.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Huangw
 * @date 2020-04-01 16:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    /**
     * Create Information for Product
     * @param productDTO
     * @return
     */
    public ProductDTO create(ProductDTO productDTO) {

        ProductCategory productCategory = productCategoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() -> new ProductCategoryResourceException("Product Category not found"));

        Product product = productMapper.toEntity(productDTO);
        product.setCategory(productCategory);
        productRepository.save(product);
        log.debug("Created Information for Product: {}", product);
        return productMapper.toDto(product);
    }

    /**
     * Find Product By Id
     * @param id
     * @return
     */
    public Optional<ProductDTO> getById(Long id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    public Optional<ProductDTO> update(Long id, ProductDTO productDTO) {
        return Optional.of(productRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(product -> {
                product.setName(productDTO.getName());
                productRepository.save(product);
                log.debug("Changed Information for Product : {}", product);
                return product;
            }).map(productMapper::toDto);
    }

    public void delete(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

    public boolean hasProductInCategory(Long categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

}
