package com.jahid.ecommerce.api.order_item;

import com.jahid.ecommerce.api.order.Order;
import com.jahid.ecommerce.api.order.OrderRepository;
import com.jahid.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, ModelMapper modelMapper,
                            OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
    }

    public ResponseOrderItemDto updateOrderItem(RequestOrderItemDto requestOrderItemDto){
        OrderItem orderItem = orderItemRepository.findById(requestOrderItemDto.getOrderItemId())
                .orElseThrow(()->new NotFoundException(requestOrderItemDto.getOrderId(),OrderItem.class.getSimpleName()));

        // update the order and orderItem
        Order order = orderItem.getOrder();
        order.setTotalQuantity(order.getTotalQuantity() - orderItem.getItemQuantity());
        order.setTotalPrice(order.getTotalPrice() - orderItem.getTotalPrice());
        orderItem.setItemQuantity(requestOrderItemDto.getItemQuantity());
        orderItem.setTotalPrice(((long) orderItem.getUnitPrice() * requestOrderItemDto.getItemQuantity()));
        order.setTotalQuantity(order.getTotalQuantity() + orderItem.getItemQuantity());
        order.setTotalPrice(order.getTotalPrice() + orderItem.getTotalPrice());
        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(orderItem,ResponseOrderItemDto.class);
    }
}