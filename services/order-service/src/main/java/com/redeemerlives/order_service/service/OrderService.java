package com.redeemerlives.order_service.service;

import com.redeemerlives.order_service.clients.CustomerServiceClient;
import com.redeemerlives.order_service.clients.PaymentServiceClient;
import com.redeemerlives.order_service.clients.ProductServiceClient;
import com.redeemerlives.order_service.dto.*;
import com.redeemerlives.order_service.entity.OrderItems;
import com.redeemerlives.order_service.entity.Orders;
import com.redeemerlives.order_service.exception.OperationNotPermitted;
import com.redeemerlives.order_service.kafka.OrderProducer;
import com.redeemerlives.order_service.kafka.OrderConfirmation;
import com.redeemerlives.order_service.mapper.OrderItemMapper;
import com.redeemerlives.order_service.mapper.OrderMapper;
import com.redeemerlives.order_service.repository.OrderItemRepository;
import com.redeemerlives.order_service.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerServiceClient customerServiceClient;
    private final ProductServiceClient productServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;

    @Transactional
    public String placeOrder(OrderDto orderDto) {
        CustomerDto customer = customerServiceClient.findCustomerById(orderDto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid customer id"));
        List<ProductPurchaseResponse> purchaseResponse = productServiceClient.purchaseProducts(orderDto.orderItems())
                .orElseThrow(() -> new OperationNotPermitted("Failed to place order"));

        Orders order = orderMapper.toOrder(orderDto);
        Orders savedOrder = orderRepository.save(order);

        List<OrderItems> orderItems = purchaseResponse.stream()
                .map(item -> orderItemMapper.toOrderItem(item, order))
                .toList();
        orderItemRepository.saveAll(orderItems);

        BigDecimal totalOrderCost = orderItems.stream()
                .map(item -> item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PaymentDto paymentDto = orderMapper.toPaymentDto(savedOrder, customer, totalOrderCost, orderDto.paymentMethod());
        paymentServiceClient.createPayment(paymentDto);

        OrderConfirmation producerDto = new OrderConfirmation(savedOrder.getId(),
                savedOrder.getCreatedAt(),
                customer.firstname(),
                customer.lastname(),
                customer.email());
        orderProducer.sendOrderConfirmation(producerDto);
        return "Order placed successfully";
    }

    public PageResponse<OrderDto> getAllOrders(int page, int size, String customerId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> customerOrders = orderRepository.findAllByCustomerId(customerId, pageable);
        List<OrderDto> data = customerOrders.stream().map(orderMapper::toOrderDto).toList();

        return new PageResponse<>(
                data,
                customerOrders.isFirst(),
                customerOrders.isLast(),
                customerOrders.getTotalElements(),
                customerOrders.getTotalPages()
        );
    }
}
