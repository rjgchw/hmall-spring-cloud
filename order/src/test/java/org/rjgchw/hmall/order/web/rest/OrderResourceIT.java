package org.rjgchw.hmall.order.web.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rjgchw.hmall.common.util.JsonUtil;
import org.rjgchw.hmall.order.IntegrationTest;
import org.rjgchw.hmall.order.config.TestFeignConfiguration;
import org.rjgchw.hmall.order.entity.Order;
import org.rjgchw.hmall.order.entity.OrderItem;
import org.rjgchw.hmall.order.entity.Product;
import org.rjgchw.hmall.order.entity.ProductCategory;
import org.rjgchw.hmall.order.repository.OrderRepository;
import org.rjgchw.hmall.order.repository.ProductCategoryRepository;
import org.rjgchw.hmall.order.repository.ProductRepository;
import org.rjgchw.hmall.order.service.dto.OrderDTO;
import org.rjgchw.hmall.order.service.dto.OrderItemDTO;
import org.rjgchw.hmall.order.service.enums.OrderStatusEnum;
import org.rjgchw.hmall.order.service.enums.PayTypeEnum;
import org.rjgchw.hmall.order.service.enums.SourceTypeEnum;
import org.rjgchw.hmall.order.web.rest.mock.StorageMocks;
import org.rjgchw.hmall.order.web.rest.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 订单资源
 *
 * @author Huangw
 * @date 2020-04-07 18:15
 */
@AutoConfigureMockMvc
@WithMockUser(username = OrderResourceIT.DEFAULT_USERNAME)
@ContextConfiguration(classes = { TestFeignConfiguration.class })
@IntegrationTest
@ExtendWith(SpringExtension.class)
public class OrderResourceIT {

    private static final Long DEFAULT_RECEIVER_ID = 1L;
    private static final Long DEFAULT_PRODUCT_ID_1 = 1L;
    private static final Long DEFAULT_PRODUCT_ID_2 = 2L;

    private static final String DEFAULT_ORDER_SN = "202003030101000001";
    public static final String DEFAULT_USERNAME = "11111111111";

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private MockMvc restMockMvc;

    private Order order;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public static Order createEntity() {
        Order order = new Order();
        order.setReceiverId(DEFAULT_RECEIVER_ID);
        order.setPayType(PayTypeEnum.ALIPAY);
        order.setSourceType(SourceTypeEnum.PC);
        order.setTotalAmount(BigDecimal.valueOf(2000));
        order.setStatus(OrderStatusEnum.PENDING_PAYMENT);
        order.setMemberUsername(DEFAULT_USERNAME);
        order.setOrderSn(DEFAULT_ORDER_SN);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderSn(DEFAULT_ORDER_SN);
        orderItem1.setOrder(order);
        orderItem1.setProductId(DEFAULT_PRODUCT_ID_1);
        orderItem1.setProductPrice(BigDecimal.valueOf(100));
        orderItem1.setProductQuantity(10);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderSn(DEFAULT_ORDER_SN);
        orderItem2.setOrder(order);
        orderItem2.setProductId(DEFAULT_PRODUCT_ID_2);
        orderItem2.setProductPrice(BigDecimal.valueOf(500));
        orderItem2.setProductQuantity(2);

        order.setItems(Stream.of(orderItem1, orderItem2).collect(Collectors.toSet()));
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity();
    }

    @Test
    @Transactional
    public void should_create_success_if_input_normally() throws Exception {

        StorageMocks.setupMockDeductResponse(mockServer);

        ProductCategory productCategory = productCategoryRepository.saveAndFlush(ProductCategoryResourceIT.createEntity());
        Product product = productRepository.saveAndFlush(ProductResourceIT.createEntity(productCategory));

        OrderVO orderVO = new OrderVO();
        orderVO.setPayType(PayTypeEnum.ALIPAY);
        orderVO.setReceiverId(DEFAULT_RECEIVER_ID);
        orderVO.setSourceType(SourceTypeEnum.APP);

        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setProductId(product.getId());
        orderItem.setProductPrice(BigDecimal.valueOf(100));
        orderItem.setProductQuantity(1);
        orderVO.setItems(Stream.of(orderItem).collect(Collectors.toSet()));

        OrderDTO orderRsp = JsonUtil.nonDefaultMapper().fromJson(
            restMockMvc.perform(
                post("/api/orders")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.nonDefaultMapper().toJson(orderVO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(),
            OrderDTO.class);

        Optional<Order> orderDb = orderRepository.findById(orderRsp.getId());
        assertThat(orderDb.isPresent()).isTrue();
        Order order = orderDb.get();
        assertThat(order.getReceiverId()).isEqualTo(DEFAULT_RECEIVER_ID);
        assertThat(order.getItems().size()).isEqualTo(1);
        assertThat(order.getMemberUsername()).isEqualTo(DEFAULT_USERNAME);
    }

    @Test
    @Transactional
    public void should_create_failure_if_input_a_empty_source_type() throws Exception {
        ProductCategory productCategory = productCategoryRepository.saveAndFlush(ProductCategoryResourceIT.createEntity());
        Product product = productRepository.saveAndFlush(ProductResourceIT.createEntity(productCategory));

        OrderVO orderVO = new OrderVO();
        orderVO.setPayType(PayTypeEnum.ALIPAY);
        orderVO.setReceiverId(DEFAULT_RECEIVER_ID);

        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setProductId(product.getId());
        orderItem.setProductPrice(BigDecimal.valueOf(100));
        orderItem.setProductQuantity(10);
        orderVO.setItems(Stream.of(orderItem).collect(Collectors.toSet()));

        restMockMvc.perform(
            post("/api/orders")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(orderVO)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_create_failure_if_input_a_empty_pay_type() throws Exception {
        ProductCategory productCategory = productCategoryRepository.saveAndFlush(ProductCategoryResourceIT.createEntity());
        Product product = productRepository.saveAndFlush(ProductResourceIT.createEntity(productCategory));

        OrderVO orderVO = new OrderVO();
        orderVO.setSourceType(SourceTypeEnum.APP);
        orderVO.setReceiverId(DEFAULT_RECEIVER_ID);

        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setProductId(product.getId());
        orderItem.setProductPrice(BigDecimal.valueOf(100));
        orderItem.setProductQuantity(10);
        orderVO.setItems(Stream.of(orderItem).collect(Collectors.toSet()));

        restMockMvc.perform(
            post("/api/orders")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(orderVO)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_create_failure_if_input_a_empty_items() throws Exception {

        OrderVO orderVO = new OrderVO();
        orderVO.setSourceType(SourceTypeEnum.APP);
        orderVO.setPayType(PayTypeEnum.ALIPAY);
        orderVO.setReceiverId(DEFAULT_RECEIVER_ID);

        restMockMvc.perform(
            post("/api/orders")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(orderVO)))
            .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void should_find_one_if_input_a_id() throws Exception {
        orderRepository.saveAndFlush(order);

        restMockMvc.perform(
            get("/api/orders/{id}", order.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.memberUsername").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.receiverId").value(DEFAULT_RECEIVER_ID))
            .andExpect(jsonPath("$.orderSn").value(DEFAULT_ORDER_SN));

    }

    @Test
    @Transactional
    public void should_find_nothing_if_input_a_non_existing_id() throws Exception {
        restMockMvc.perform(get("/api/orders/{id}", "-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void should_find_something_if_input_a_name() throws Exception {
        orderRepository.saveAndFlush(order);

        defaultOrderShouldBeFound("status.equals=" + OrderStatusEnum.PENDING_PAYMENT);
        defaultOrderShouldNotBeFound("status.equals=" + OrderStatusEnum.FINISHED);
    }

    private void defaultOrderShouldNotBeFound(String filter) throws Exception {
        restMockMvc.perform(
            get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    private void defaultOrderShouldBeFound(String filter) throws Exception {
        restMockMvc.perform(
            get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[*].status").value(hasItem(OrderStatusEnum.PENDING_PAYMENT.toString())));
    }

    @Test
    @Transactional
    public void should_find_all_if_not_input() throws Exception {
        orderRepository.saveAndFlush(order);

        restMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].status").value(hasItem(OrderStatusEnum.PENDING_PAYMENT.toString())));
    }

}
