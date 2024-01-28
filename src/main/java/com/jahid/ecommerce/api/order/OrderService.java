package com.jahid.ecommerce.api.order;

import com.jahid.ecommerce.api.cart.Cart;
import com.jahid.ecommerce.api.cart.CartRepository;
import com.jahid.ecommerce.api.order_item.OrderItem;
import com.jahid.ecommerce.api.order_item.RequestOrderItemDto;
import com.jahid.ecommerce.api.order_timeline.OrderTimeline;
import com.jahid.ecommerce.api.product.Product;
import com.jahid.ecommerce.api.product.ProductRepository;
import com.jahid.ecommerce.api.utility.EnumConstants;
import com.jahid.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper,
                        CartRepository cartRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Todo : create order is incomplete
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto){
        Cart cart = cartRepository.findById(orderRequestDto.getCartId()).orElseThrow(()-> new NotFoundException(orderRequestDto.getCartId(), Cart.class.getSimpleName()));

        //create a orderTime
        OrderTimeline orderTimeline = new OrderTimeline();
        orderTimeline.setOrderTimelineId(null);
        orderTimeline.setOrderPlaceDateTime(LocalDateTime.now());

        // create Order and set its attributes
        Order order = new Order();
        order.setOrderId(null);
        order.setUser(cart.getUser());
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setOrderStatus(EnumConstants.OderStatus.PENDING);
        order.setOrderTimeline(orderTimeline);

        // get orderItems from cartItems
        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(null);
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setItemQuantity(cartItem.getItemQuantity());
            orderItem.setOrder(order);
            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);

        Order createdOrder = orderRepository.save(order);

        // delete the cart after order is placed
        cartRepository.deleteById(cart.getCartId());

        return modelMapper.map(createdOrder, OrderResponseDto.class);
    }

    public OrderResponseDto addToOrder(RequestOrderItemDto requestOrderItemDto){
        Order order = orderRepository.findById(requestOrderItemDto.getOrderId())
                .orElseThrow(()-> new NotFoundException(requestOrderItemDto.getOrderItemId(),
                        Order.class.getSimpleName()));
        Product product = productRepository.findById(requestOrderItemDto.getProductId())
                .orElseThrow(()-> new NotFoundException(requestOrderItemDto.getProductId(),
                        Product.class.getSimpleName()));

        // create a new orderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(null);
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setTotalPrice(((long) product.getPrice() * requestOrderItemDto.getItemQuantity()));
        orderItem.setProduct(product);
        orderItem.setItemQuantity(requestOrderItemDto.getItemQuantity());
        orderItem.setOrder(order);

        // update the order info
        order.setTotalQuantity(order.getTotalQuantity() + orderItem.getItemQuantity());
        order.setTotalPrice(order.getTotalPrice() + orderItem.getTotalPrice());
        order.getOrderItems().add(orderItem);
        return modelMapper.map(orderRepository.save(order), OrderResponseDto.class);
    }

    public OrderResponseDto getOrderById(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new NotFoundException(orderId,Order.class.getSimpleName()));
        return modelMapper.map(order, OrderResponseDto.class);
    }

    public List<OrderResponseDto> getAllOrder(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
    }

    public void deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId, Order.class.getSimpleName()));
        orderRepository.deleteById(orderId);
    }

    // admin only
    public OrderResponseDto approveOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId, Order.class.getSimpleName()));
        order.setOrderStatus(EnumConstants.OderStatus.APPROVE);
        order.getOrderTimeline().setOrderApproveDateTime(LocalDateTime.now());
        return modelMapper.map(orderRepository.save(order), OrderResponseDto.class);
    }

    public OrderResponseDto updateReadyToDeliver(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId, Order.class.getSimpleName()));
        order.getOrderTimeline().setReadyToDeliverDateTime(LocalDateTime.now());
        return modelMapper.map(orderRepository.save(order), OrderResponseDto.class);
    }

    public OrderResponseDto cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId, Order.class.getSimpleName()));
        order.setOrderStatus(EnumConstants.OderStatus.CANCELED);
        return modelMapper.map(orderRepository.save(order), OrderResponseDto.class);
    }

    public OrderResponseDto updateDeliveryDateOfOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId, Order.class.getSimpleName()));
        order.setOrderStatus(EnumConstants.OderStatus.DELIVERED);
        order.getOrderTimeline().setDeliveryDateTime(LocalDateTime.now());
        return modelMapper.map(orderRepository.save(order), OrderResponseDto.class);
    }
}
