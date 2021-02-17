package org.rjgchw.hmall.order.service;

import org.redisson.api.RedissonClient;
import org.rjgchw.hmall.order.entity.Order;
import org.rjgchw.hmall.order.entity.OrderItem;
import org.rjgchw.hmall.order.repository.OrderRepository;
import org.rjgchw.hmall.order.service.dto.OrderDTO;
import org.rjgchw.hmall.order.service.dto.OrderItemDTO;
import org.rjgchw.hmall.order.service.enums.OrderStatusEnum;
import org.rjgchw.hmall.order.service.enums.PayTypeEnum;
import org.rjgchw.hmall.order.service.enums.SourceTypeEnum;
import org.rjgchw.hmall.order.service.mapper.OrderItemMapper;
import org.rjgchw.hmall.order.service.mapper.OrderMapper;
import org.rjgchw.hmall.order.service.error.LockStockFailException;
import org.rjgchw.hmall.order.service.error.ProductDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 订单 service
 * @author Huangw
 * @date 2020-04-05 20:10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final RedissonClient redissonClient;
    private final OrderItemMapper orderItemMapper;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, ProductService productService, RedissonClient redissonClient, OrderItemMapper orderItemMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.redissonClient = redissonClient;
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDTO create(SourceTypeEnum sourceType, PayTypeEnum payType, Long receiverId, Set<OrderItemDTO> orderItems, String memberPhone) {
        // 锁定库存
        lockStock(orderItems);
        Order order = new Order();
        order.setSourceType(sourceType);
        order.setPayType(payType);
        order.setReceiverId(receiverId);
        order.setMemberUsername(memberPhone);
        // 生成订单号
        order.setOrderSn(generateOrderSn(sourceType, payType));
        order.setStatus(OrderStatusEnum.PENDING_PAYMENT);
        // 计算订单总额
        order.setTotalAmount(calcTotalAmount(orderItems));
        order.setItems(orderItems.stream().map(x -> {
            OrderItem orderItem = orderItemMapper.toEntity(x);
            orderItem.setOrder(order);
            orderItem.setOrderSn(order.getOrderSn());
            return orderItem;
        }).collect(Collectors.toSet()));
        orderRepository.save(order);
        log.debug("Created Information for Order: {}", order);
        return orderMapper.toDto(order);
    }

    public Optional<OrderDTO> getById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    /**
     * 锁定商品库存
     * @param orderItems
     */
    private void lockStock(Set<OrderItemDTO> orderItems) {
        orderItems.forEach(item -> {
            if(!productService.getById(item.getProductId()).isPresent()) {
                throw new ProductDoesNotExistException();
            }
//            boolean lockResult = stockService.lockStock(item.getProductId(), item.getProductQuantity());
            boolean lockResult = true;
            if (!lockResult) {
                // 锁定库存失败 可能存在资源竞争问题
                throw new LockStockFailException();
            }
        });
    }

    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     */
    private String generateOrderSn(SourceTypeEnum sourceType, PayTypeEnum payType) {
        Long increment = redissonClient.getAtomicLong("ORDER_INC").addAndGet(1);
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        sb.append(date);
        sb.append(String.format("%02d", sourceType.getValue()));
        sb.append(String.format("%02d", payType.getValue()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    /**
     * 计算总金额
     */
    private BigDecimal calcTotalAmount(Set<OrderItemDTO> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OrderItemDTO item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }
}
