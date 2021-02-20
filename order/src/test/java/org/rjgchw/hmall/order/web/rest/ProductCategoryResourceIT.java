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
import org.rjgchw.hmall.order.service.dto.ProductCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Huangw
 * @date 2020-04-01 16:50
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
public class ProductCategoryResourceIT {

    private static final String DEFAULT_NAME = "FOOTBALL";
    private static final String UPDATED_NAME = "BASKETBALL";

    @Autowired
    private MockMvc restMockMvc;

    private ProductCategory productCategory;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public static ProductCategory createEntity() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(DEFAULT_NAME);
        return productCategory;
    }

    @BeforeEach
    public void initTest() {
        productCategory = createEntity();
    }

    @Test
    @Transactional
    public void should_create_success_if_input_normally() throws Exception {

        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        productCategoryDTO.setName(DEFAULT_NAME);

        ProductCategoryDTO productCategoryRsp = JsonUtil.nonDefaultMapper().fromJson(
            restMockMvc.perform(
                post("/api/product-categories")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(),
            ProductCategoryDTO.class);

        Optional<ProductCategory> productCategoryDb = productCategoryRepository.findById(productCategoryRsp.getId());
        assertThat(productCategoryDb.isPresent()).isTrue();
        assertThat(productCategoryDb.get().getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void should_create_failure_if_input_a_existing_name() throws Exception {
        productCategoryRepository.saveAndFlush(productCategory);

        ProductCategoryDTO productCategoryReq = new ProductCategoryDTO();
        productCategoryReq.setName(DEFAULT_NAME);

        restMockMvc.perform(
            post("/api/product-categories")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(productCategoryReq)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_find_one_if_input_a_id() throws Exception {
        productCategoryRepository.saveAndFlush(productCategory);

        restMockMvc.perform(
            get("/api/product-categories/{id}", productCategory.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(productCategory.getName()));
    }

    @Test
    @Transactional
    public void should_find_nothing_if_input_a_non_existing_id() throws Exception {
        restMockMvc.perform(get("/api/product-categories/{id}", "-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void should_find_something_if_input_a_name() throws Exception {
        productCategoryRepository.saveAndFlush(productCategory);

        defaultProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);
        defaultProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    private void defaultProductCategoryShouldNotBeFound(String filter) throws Exception {
        restMockMvc.perform(
            get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    private void defaultProductCategoryShouldBeFound(String filter) throws Exception {
        restMockMvc.perform(
            get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void should_find_all_if_not_input() throws Exception {
        productCategoryRepository.saveAndFlush(productCategory);

        restMockMvc.perform(get("/api/product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void should_update_success_if_input_a_name() throws Exception {
        productCategoryRepository.saveAndFlush(productCategory);

        String updatedName = "UpdatedName";
        ProductCategoryDTO updatedProductCategory = new ProductCategoryDTO();
        updatedProductCategory.setName(updatedName);

        restMockMvc.perform(
            put("/api/product-categories/{id}", productCategory.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedProductCategory))
        ).andExpect(status().isOk());

        Optional<ProductCategory> testProductCategory = productCategoryRepository.findById(productCategory.getId());
        assertThat(testProductCategory.isPresent()).isTrue();
        assertThat(testProductCategory.get().getName()).isEqualTo(updatedName);
    }

    @Test
    @Transactional
    public void should_update_failure_if_input_a_existing_name() throws Exception {

        productCategoryRepository.saveAndFlush(productCategory);

        String updatedName = "UpdatedName111";

        // init another testProductCategory
        ProductCategory anotherProductCategory = new ProductCategory();
        anotherProductCategory.setName(updatedName);

        productCategoryRepository.saveAndFlush(anotherProductCategory);

        ProductCategoryDTO updatedProductCategory = new ProductCategoryDTO();
        updatedProductCategory.setName(updatedName);

        // update testProductCategory
        restMockMvc.perform(
            put("/api/product-categories/{id}", productCategory.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(updatedProductCategory))
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_delete_success_if_input_a_id() throws Exception {
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        restMockMvc.perform(
            delete("/api/product-categories/{id}", productCategory.getId())
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void should_delete_nothing_if_input_a_non_existing_id() throws Exception {
        productCategory = productCategoryRepository.saveAndFlush(productCategory);
        Product product = ProductResourceIT.createEntity(productCategory);
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        restMockMvc.perform(
            delete("/api/product-categories/{id}", productCategory.getId())
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnprocessableEntity());

    }

}
