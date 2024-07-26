package com.ecommerce.api.order.service;

import com.ecommerce.api.order.model.Order;
import com.ecommerce.api.order.request.OrderItemRequest;
import com.ecommerce.api.order.response.OrderItemResponse;
import com.ecommerce.api.order.model.OrderItem;
import com.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    public OrderItemService( OrderItemRepository orderItemRepository, ModelMapper modelMapper,
                            OrderRepository orderRepository ) {
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
    }

    public OrderItemResponse updateOrderItem(OrderItemRequest orderItemRequest){
        OrderItem orderItem = orderItemRepository.findById(orderItemRequest.getOrderItemId())
                .orElseThrow(()->new NotFoundException(orderItemRequest.getOrderId(),OrderItem.class.getSimpleName()));

        // update the order and orderItem
        Order order = orderItem.getOrder();
        order.setTotalQuantity(order.getTotalQuantity() - orderItem.getItemQuantity());
        order.setTotalPrice(order.getTotalPrice() - orderItem.getTotalPrice());
        orderItem.setItemQuantity(orderItemRequest.getItemQuantity());
        orderItem.setTotalPrice(((long) orderItem.getUnitPrice() * orderItemRequest.getItemQuantity()));
        order.setTotalQuantity(order.getTotalQuantity() + orderItem.getItemQuantity());
        order.setTotalPrice(order.getTotalPrice() + orderItem.getTotalPrice());
        orderRepository.save(order);
        return modelMapper.map(orderItem, OrderItemResponse.class);
    }

    public void deleteOrderItem( long orderItemId ) {
        OrderItem orderItem = orderItemRepository.findById( orderItemId )
                .orElseThrow( ()->new NotFoundException( orderItemId,
                        OrderItem.class.getSimpleName() ) );
        Order order = orderItem.getOrder();
        if( order.getOrderItems().isEmpty() ){
            orderRepository.deleteById( order.getOrderId() );
        }else {
            order.setTotalPrice( order.getTotalPrice()-orderItem.getTotalPrice() );
            order.setTotalQuantity( order.getTotalQuantity() - orderItem.getItemQuantity() );
            order.getOrderItems().remove( orderItem );
            orderRepository.save( order );
        }
    }
}
