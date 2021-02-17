package org.rjgchw.hmall.order.service;

import org.rjgchw.hmall.order.entity.ProductBrand;
import org.rjgchw.hmall.order.repository.ProductBrandRepository;
import org.rjgchw.hmall.order.service.dto.ProductBrandDTO;
import org.rjgchw.hmall.order.service.mapper.ProductBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Huangw
 * @date 2020-03-08 16:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductBrandService {

    private final Logger log = LoggerFactory.getLogger(ProductBrandService.class);

    private final ProductBrandMapper productBrandMapper;
    private final ProductBrandRepository productBrandRepository;

    public ProductBrandService(ProductBrandMapper productBrandMapper, ProductBrandRepository productBrandRepository) {
        this.productBrandMapper = productBrandMapper;
        this.productBrandRepository = productBrandRepository;
    }

    public ProductBrandDTO create(ProductBrandDTO productBrandDTO) {
        ProductBrand productBrand = productBrandMapper.toEntity(productBrandDTO);
        productBrandRepository.save(productBrand);
        log.debug("Created Information for ProductBrand: {}", productBrand);
        return productBrandMapper.toDto(productBrand);
    }

    public Optional<ProductBrandDTO> getById(Long id) {
        return productBrandRepository.findById(id).map(productBrandMapper::toDto);
    }

    public Optional<ProductBrandDTO> update(Long id, ProductBrandDTO productBrandDTO) {
        return Optional.of(productBrandRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(productBrand -> {
                productBrand.setName(productBrandDTO.getName());
                productBrand.setLogo(productBrandDTO.getLogo());
                productBrand.setBrandStory(productBrandDTO.getBrandStory());
                productBrand.setFirstLetter(productBrandDTO.getFirstLetter());
                productBrand.setSort(productBrandDTO.getSort());
                productBrand.setFactory(productBrandDTO.getFactory());
                productBrand.setShow(productBrandDTO.getShow());
                productBrandRepository.save(productBrand);
                log.debug("Changed Information for Product Brand: {}", productBrand);
                return productBrand;
            }).map(productBrandMapper::toDto);
    }

    public void delete(Long id) {
        productBrandRepository.findById(id).ifPresent(productBrandRepository::delete);
    }
}
