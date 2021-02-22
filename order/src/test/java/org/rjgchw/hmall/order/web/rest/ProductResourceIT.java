package org.rjgchw.hmall.order.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rjgchw.hmall.common.security.AuthoritiesConstants;
import org.rjgchw.hmall.common.util.JsonUtil;
import org.rjgchw.hmall.order.IntegrationTest;
import org.rjgchw.hmall.order.entity.Product;
import org.rjgchw.hmall.order.entity.ProductCategory;
import org.rjgchw.hmall.order.repository.ProductCategoryRepository;
import org.rjgchw.hmall.order.repository.ProductRepository;
import org.rjgchw.hmall.order.service.dto.ProductDTO;
import org.rjgchw.hmall.order.service.enums.ProductStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * @author Huangw
 * @date 2020-04-01 17:04
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
public class ProductResourceIT {

    private static final String DEFAULT_NAME = "MACBOOK";
    private static final String UPDATED_NAME = "IPHONE";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MockMvc restMockMvc;

    private Product product;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void setup() {
        cacheManager.getCache(ProductRepository.PRODUCT_BY_NAME).clear();
    }

    public static Product createEntity(ProductCategory productCategory) {
        Product product = new Product();
        product.setName(DEFAULT_NAME);
        product.setPrice(BigDecimal.ZERO);
        product.setStatus(ProductStatusEnum.UP);
        product.setDescription("This is a computer notebook");
        product.setImgUrl("https://image.com/abc.png");
        product.setCategory(productCategory);
        return product;
    }

    private ProductCategory saveAndFlushProductCategory() {
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity();
        return productCategoryRepository.saveAndFlush(productCategory);
    }

    @BeforeEach
    public void initTest() {
        ProductCategory productCategory = productCategoryRepository.saveAndFlush(ProductCategoryResourceIT.createEntity());
        product = createEntity(productCategory);
    }

    @Test
    @Transactional
    public void should_create_success_if_input_normally() throws Exception {
        ProductCategory productCategory = saveAndFlushProductCategory();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(DEFAULT_NAME);
        productDTO.setPrice(BigDecimal.ZERO);
        productDTO.setStatus(ProductStatusEnum.UP);
        productDTO.setDescription("This is a computer notebook");
        productDTO.setImgUrl("http://image.com/abc.png");
        productDTO.setCategoryId(productCategory.getId());

        ProductDTO productRsp = JsonUtil.nonDefaultMapper().fromJson(
            restMockMvc.perform(
                post("/api/products")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(),
            ProductDTO.class);

        Optional<Product> productDb = productRepository.findById(productRsp.getId());
        assertThat(productDb.isPresent()).isTrue();
        assertThat(productDb.get().getName()).isEqualTo(DEFAULT_NAME);
        assertThat(productDb.get().getCategory()).isNotNull();
        assertThat(productDb.get().getCategory().getId()).isEqualTo(productCategory.getId());
    }

    @Test
    @Transactional
    public void should_create_failure_if_input_a_existing_name() throws Exception {

        productRepository.saveAndFlush(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(DEFAULT_NAME);
        productDTO.setPrice(BigDecimal.ZERO);
        productDTO.setStatus(ProductStatusEnum.UP);
        productDTO.setDescription("This is a computer notebook");
        productDTO.setImgUrl("http://image.com/abc.png");
        productDTO.setCategoryId(product.getCategory().getId());

        restMockMvc.perform(
            post("/api/products")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_find_one_if_input_a_id() throws Exception {
        productRepository.saveAndFlush(product);

        restMockMvc.perform(
            get("/api/products/{id}", product.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.categoryId").value(product.getCategory().getId()));
    }

    @Test
    @Transactional
    public void should_find_nothing_if_input_a_non_existing_id() throws Exception {
        restMockMvc.perform(get("/api/products/{id}", "-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void should_find_something_if_input_a_name() throws Exception {
        productRepository.saveAndFlush(product);

        defaultProductShouldBeFound("name.equals=" + DEFAULT_NAME);
        defaultProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void should_find_something_if_input_a_category_id() throws Exception {
        productRepository.saveAndFlush(product);

        defaultProductShouldBeFound("categoryId.equals=" + product.getCategory().getId());
        defaultProductShouldNotBeFound("categoryId.equals=" + "-1");
    }

    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restMockMvc.perform(
            get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    private void defaultProductShouldBeFound(String filter) throws Exception {
        restMockMvc.perform(
            get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void should_find_all_if_not_input() throws Exception {
        productRepository.saveAndFlush(product);

        restMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void should_update_success_if_input_a_name() throws Exception {
        productRepository.saveAndFlush(product);

        String updatedName = "UpdatedName";
        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setName(updatedName);
        updatedProduct.setCategoryId(product.getCategory().getId());
        updatedProduct.setStatus(ProductStatusEnum.UP);

        restMockMvc.perform(
            put("/api/products/{id}", product.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
        ).andExpect(status().isOk());

        Optional<Product> testProduct = productRepository.findById(product.getId());
        assertThat(testProduct.isPresent()).isTrue();
        assertThat(testProduct.get().getName()).isEqualTo(updatedName);
    }

    @Test
    @Transactional
    public void should_update_failure_if_input_a_existing_name() throws Exception {

        productRepository.saveAndFlush(product);

        String updatedName = "UpdatedName111";

        // init another product
        Product anotherProduct = new Product();
        anotherProduct.setName(updatedName);
        anotherProduct.setCategory(product.getCategory());

        productRepository.saveAndFlush(anotherProduct);

        ProductDTO updatedProduct = new ProductDTO();
        updatedProduct.setName(updatedName);
        updatedProduct.setCategoryId(product.getCategory().getId());

        // update product
        restMockMvc.perform(
            put("/api/products/{id}", product.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_delete_success_if_input_a_id() throws Exception {
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        restMockMvc.perform(
            delete("/api/products/{id}", product.getId())
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void should_delete_nothing_if_input_a_non_existing_id() throws Exception {
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        restMockMvc.perform(
            delete("/api/products/{id}", product.getId())
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
