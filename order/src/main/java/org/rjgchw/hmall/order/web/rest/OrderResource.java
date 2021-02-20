package org.rjgchw.hmall.order.web.rest;

import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.rjgchw.hmall.common.security.SecurityWebUtils;
import org.rjgchw.hmall.common.util.UriUtil;
import org.rjgchw.hmall.common.web.rest.error.ResourceNotFoundAlertException;
import org.rjgchw.hmall.order.service.OrderQueryService;
import org.rjgchw.hmall.order.service.OrderService;
import org.rjgchw.hmall.order.service.dto.OrderCriteria;
import org.rjgchw.hmall.order.service.dto.OrderDTO;
import org.rjgchw.hmall.order.web.rest.vo.OrderVO;
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


/**
 * 订单
 *
 * @author Huangw
 * @date 2020-03-08 16:52
 */
//@Tag(name = "Order")
@RestController
@RequestMapping("/api")
public class OrderResource {

    private final Logger log = LoggerFactory.getLogger(OrderResource.class);
    private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    private final JHipsterProperties jHipsterProperties;

    public OrderResource(OrderService orderService, OrderQueryService orderQueryService, JHipsterProperties jHipsterProperties) {
        this.orderService = orderService;
        this.orderQueryService = orderQueryService;
        this.jHipsterProperties = jHipsterProperties;
    }

//    @Operation(
//        description = "创建订单",
//        security = @SecurityRequirement(name = "jwt"),
//        responses = {
//            @ApiResponse(responseCode = "201",
//                content = @Content(schema = @Schema(implementation = OrderVO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
//                description = "成功"
//            )
//        }
//    )
    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderVO orderDTO) {
        log.debug("REST request to create order {}:", orderDTO);
        return SecurityWebUtils.getCurrentUserLogin().map(memberPhone -> {
            OrderDTO newOrder = orderService.create(orderDTO.getSourceType(),
                orderDTO.getPayType(),
                orderDTO.getReceiverId(),
                orderDTO.getItems(),
                memberPhone);

            return ResponseEntity.created(UriUtil.createUri("/api/orders/" + newOrder.getId()))
                .headers(HeaderUtil.createAlert(jHipsterProperties.getClientApp().getName(),
                    "orderManagement.created", String.valueOf(newOrder.getId())))
                .body(newOrder);
        }).orElseThrow(() -> new ResourceNotFoundAlertException("Member not found"));
    }

//    @Operation(
//        description = "获取订单信息",
//        security = @SecurityRequirement(name = "jwt"),
//        responses = {
//            @ApiResponse(responseCode = "200",
//                content = @Content(schema = @Schema(implementation = OrderDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE),
//                description = "成功"
//            )
//        }
//    )
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrder(
//        @Parameter(description = "订单id", required = true)
        @PathVariable Long id) {
        log.debug("REST request to get product brand id: {}", id);

        return ResponseUtil.wrapOrNotFound(orderService.getById(id));
    }

//    @Operation(
//        description = "获取订单列表",
//        security = @SecurityRequirement(name = "jwt"),
//        responses = {
//            @ApiResponse(responseCode = "200",
//                content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class)), mediaType = MediaType.APPLICATION_JSON_VALUE),
//                description = "成功"
//            )
//        }
//    )
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders (OrderCriteria criteria, Pageable pageable) {
        log.debug("REST request to get product brand: {}", pageable);

        final Page<OrderDTO> page = orderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
