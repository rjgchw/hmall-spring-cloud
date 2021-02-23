package org.rjgchw.hmall.order.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.rjgchw.hmall.order.service.mapper.ProductMapper;
import org.springframework.http.MediaType;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.rjgchw.hmall.common.util.UriUtil;
import org.rjgchw.hmall.order.repository.ProductRepository;
import org.rjgchw.hmall.order.repository.search.ProductSearchRepository;
import org.rjgchw.hmall.order.service.ProductQueryService;
import org.rjgchw.hmall.order.service.ProductService;
import org.rjgchw.hmall.order.service.dto.ProductCriteria;
import org.rjgchw.hmall.order.service.dto.ProductDTO;
import org.rjgchw.hmall.order.web.rest.errors.NameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * 商品
 * @author Huangw
 * @date 2020-04-01 17:55
 */
@Tag(name = "Product Tag")
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final JHipsterProperties jHipsterProperties;
    private final ProductQueryService productQueryService;
    private final ProductSearchRepository productSearchRepository;
    private final ProductMapper productMapper;

    public ProductResource(ProductRepository productRepository, ProductService productService, JHipsterProperties jHipsterProperties, ProductQueryService productQueryService, ProductSearchRepository productSearchRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.jHipsterProperties = jHipsterProperties;
        this.productQueryService = productQueryService;
        this.productSearchRepository = productSearchRepository;
        this.productMapper = productMapper;
    }

    @Operation(
        description = "创建商品",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "201",
                content = @Content(schema = @Schema(implementation = ProductDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        log.debug("REST request to create product {}:", productDTO);
        if (productRepository.findOneByName(productDTO.getName()).isPresent()) {
            throw new NameAlreadyUsedException("Product brand name already used", "product");
        }
        ProductDTO newProduct = productService.create(productDTO);

        return ResponseEntity.created(UriUtil.createUri("/api/products/" + newProduct.getId()))
            .headers(HeaderUtil.createAlert(jHipsterProperties.getClientApp().getName(),
                "productManagement.created", String.valueOf(newProduct.getId())))
            .body(newProduct);
    }

    @Operation(
        description = "删除商品",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "204", description = "成功")
        }
    )
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(
        @Parameter(description = "商品id", required = true)
        @PathVariable Long id) {
        log.debug("REST request to delete product: {}", id);

        productService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(
                HeaderUtil.createAlert(
                    jHipsterProperties.getClientApp().getName(),
                    "productManagement.deleted",
                    String.valueOf(id))).build();
    }

    @Operation(
        description = "获取商品信息",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(schema = @Schema(implementation = ProductDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(
        @Parameter(description = "商品id", required = true)
        @PathVariable Long id) {
        log.debug("REST request to get product id: {}", id);

        return ResponseUtil.wrapOrNotFound(productService.getById(id));
    }

    @Operation(
        description = "获取商品列表",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts (ProductCriteria criteria, Pageable pageable) {
        log.debug("REST request to get product: {}", pageable);

        final Page<ProductDTO> page = productQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Operation(
        description = "修改商品",
        security = @SecurityRequirement(name = "jwt"),
        responses = {
            @ApiResponse(responseCode = "200",
                content = @Content(schema = @Schema(implementation = ProductDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
                description = "成功"
            )
        }
    )
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
        @Parameter(description = "商品id", required = true)
        @PathVariable Long id,
                                                              @RequestBody @Valid ProductDTO productDTO) {
        log.debug("REST request to update product id: {}, body : {}", id, productDTO);

        if (productRepository.findOneByName(productDTO.getName()).isPresent()) {
            throw new NameAlreadyUsedException("Product brand name already used", "product");
        }
        return ResponseUtil.wrapOrNotFound(productService.update(id, productDTO),
            HeaderUtil.createAlert(jHipsterProperties.getClientApp().getName(), "productManagement.updated", String.valueOf(id)));
    }

    @GetMapping("/_search/products/{query}")
    public List<ProductDTO> search(@PathVariable String query) {
        return StreamSupport
            .stream(productSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(productMapper::toDto)
            .collect(Collectors.toList());
    }
}
