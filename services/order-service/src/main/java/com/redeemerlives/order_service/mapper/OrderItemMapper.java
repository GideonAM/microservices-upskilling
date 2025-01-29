package com.redeemerlives.order_service.mapper;

import com.redeemerlives.order_service.dto.ProductPurchaseResponse;
import com.redeemerlives.order_service.entity.OrderItems;
import com.redeemerlives.order_service.entity.Orders;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItems toOrderItem(ProductPurchaseResponse item, Orders order) {
        return OrderItems.builder()
                .productId(item.productId())
                .productQuantity(item.productQuantity())
                .productName(item.productName())
                .productPrice(item.productPrice())
                .order(order)
                .build();
    }
}
