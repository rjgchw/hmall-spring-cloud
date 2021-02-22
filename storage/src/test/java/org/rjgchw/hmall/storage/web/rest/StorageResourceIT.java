package org.rjgchw.hmall.storage.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rjgchw.hmall.common.security.AuthoritiesConstants;
import org.rjgchw.hmall.common.util.JsonUtil;
import org.rjgchw.hmall.storage.IntegrationTest;
import org.rjgchw.hmall.storage.entity.Storage;
import org.rjgchw.hmall.storage.repository.StorageRepository;
import org.rjgchw.hmall.storage.web.rest.vo.StorageDeductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Huangw
 * @date 2020-04-01 17:04
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
public class StorageResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 99999999L;
    private static final Integer DEFAULT_STORAGE = 100;

    @Autowired
    private MockMvc restMockMvc;

    private Storage storage;

    @Autowired
    private StorageRepository storageRepository;

    public static Storage createEntity() {
        Storage storage = new Storage();
        storage.setProductId(DEFAULT_PRODUCT_ID);
        storage.setStorage(DEFAULT_STORAGE);
        return storage;
    }

    @BeforeEach
    public void initTest() {
        storage = createEntity();
    }

    @Test
    @Transactional
    public void should_deduct_success_if_input_normally() throws Exception {
        storageRepository.saveAndFlush(storage);

        StorageDeductVO storageDeductVO = new StorageDeductVO();
        storageDeductVO.setProductId(storage.getProductId());
        storageDeductVO.setProductQuantity(1);
        restMockMvc.perform(
            post("/api/storages/deduct")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.nonDefaultMapper().toJson(storageDeductVO)))
            .andExpect(status().isOk());

        storageRepository.findById(storage.getId()).ifPresent(x -> {
            assertThat(x.getStorage()).isEqualTo(storage.getStorage() - storageDeductVO.getProductQuantity());
        });
    }

    @Test
    @Transactional
    public void should_deduct_failure_if_input_a_non_existing_product_id() throws Exception {

        StorageDeductVO storageDeductVO = new StorageDeductVO();
        storageDeductVO.setProductId(storage.getProductId());
        storageDeductVO.setProductQuantity(1);

        restMockMvc.perform(
            post("/api/storages/deduct")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.nonDefaultMapper().toJson(storageDeductVO)))
            .andExpect(status().isUnprocessableEntity());
    }
}
