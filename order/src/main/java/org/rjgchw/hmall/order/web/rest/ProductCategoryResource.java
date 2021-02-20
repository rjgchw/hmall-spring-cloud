package org.rjgchw.hmall.order.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rjgchw.hmall.common.util.UriUtil;
import org.rjgchw.hmall.order.repository.ProductCategoryRepository;
import org.rjgchw.hmall.order.service.ProductCategoryQueryService;
import org.rjgchw.hmall.order.service.ProductCategoryService;
import org.rjgchw.hmall.order.service.dto.ProductCategoryCriteria;
import org.rjgchw.hmall.order.service.dto.ProductCategoryDTO;
import org.rjgchw.hmall.order.service.error.CategoryHasChildException;
import org.rjgchw.hmall.order.web.rest.errors.NameAlreadyUsedException;
import org.rjgchw.hmall.order.web.rest.errors.UnDeletableCategoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import java.util.List;


/**
 * 商品类别管理
 * @author Huangw
 * @date 2020-04-01 15:59
 */
@Tag(name = "ProductCategory")
@RestController
@RequestMapping("/api")
public class ProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryResource.class);
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryService productCategoryService;
    private final JHipsterProperties jHipsterProperties;
    private final ProductCategoryQueryService productCategoryQueryService;

    public ProductCategoryResource(ProductCategoryRepository productCategoryRepository, ProductCategoryService productCategoryService, JHipsterProperties jHipsterProperties, ProductCategoryQueryService productCategoryQueryService) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategoryService = productCategoryService;
        this.jHipsterProperties = jHipsterProperties;
        this.productCategoryQueryService = productCategoryQueryService;
    }

    @Operation(
        description = "创建商品类别",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "201",
                content = @Content(schema = @Schema(implementation = ProductCategoryDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )

    @PostMapping("/product-categories")
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@RequestBody @Valid ProductCategoryDTO productCategoryDTO) {
        log.debug("REST request to create productCategory {}:", productCategoryDTO);
        if (productCategoryRepository.findOneByName(productCategoryDTO.getName()).isPresent()) {
            throw new NameAlreadyUsedException("Product brand name already used", "productCategory");
        }
        ProductCategoryDTO newProductCategory = productCategoryService.create(productCategoryDTO);

        return ResponseEntity.created(UriUtil.createUri("/api/product-categories/" + newProductCategory.getId()))
            .headers(HeaderUtil.createAlert(jHipsterProperties.getClientApp().getName(),
                "productCategoryManagement.created", String.valueOf(newProductCategory.getId())))
            .body(newProductCategory);
    }

    @Operation(
        description = "删除商品类别",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "204", description = "成功")
        }
    )
    @DeleteMapping("/product-categories/{id}")
    public ResponseEntity<Void> deleteProductCategory(@Parameter(description = "商品类别id", required = true) @PathVariable Long id) {
        log.debug("REST request to delete productCategory: {}", id);

        try {
            productCategoryService.delete(id);
        } catch (CategoryHasChildException e) {
            throw new UnDeletableCategoryException(e.getMessage());
        }
        return ResponseEntity
            .noContent()
            .headers(
                HeaderUtil.createAlert(
                    jHipsterProperties.getClientApp().getName(),
                    "productCategoryManagement.deleted",
                    String.valueOf(id))).build();
    }

    @Operation(
        description = "获取商品类别信息",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(schema = @Schema(implementation = ProductCategoryDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )
    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategory(@Parameter(description = "商品类别id", required = true) @PathVariable Long id) {
        log.debug("REST request to get productCategory id: {}", id);
        return ResponseUtil.wrapOrNotFound(productCategoryService.getById(id));
    }

    @Operation(
        description = "获取商品类别列表",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductCategoryDTO.class)), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )

    @GetMapping("/product-categories")
    public ResponseEntity<List<ProductCategoryDTO>> getProductCategories (ProductCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get productCategory: {}", pageable);

        final Page<ProductCategoryDTO> page = productCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Operation(
        description = "修改商品类别",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(schema = @Schema(implementation = ProductCategoryDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )
    @PutMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(@Parameter(description = "商品类别id", required = true) @PathVariable Long id,
                                                              @RequestBody @Valid ProductCategoryDTO productCategoryDTO) {
        log.debug("REST request to update productCategory id: {}, body : {}", id, productCategoryDTO);

        if (productCategoryRepository.findOneByName(productCategoryDTO.getName()).isPresent()) {
            throw new NameAlreadyUsedException("Product brand name already used", "productCategory");
        }
        return ResponseUtil.wrapOrNotFound(productCategoryService.update(id, productCategoryDTO),
            HeaderUtil.createAlert(jHipsterProperties.getClientApp().getName(), "productCategoryManagement.updated", String.valueOf(id)));
    }
}
