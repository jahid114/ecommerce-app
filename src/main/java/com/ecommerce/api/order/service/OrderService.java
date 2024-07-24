package com.ecommerce.api.order.service;

import com.ecommerce.api.order.request.OrderRequest;
import com.ecommerce.api.order.response.OrderResponse;
import com.ecommerce.api.order.model.Order;
import com.ecommerce.api.order_item.OrderItem;
import com.ecommerce.api.product.Product;
import com.ecommerce.api.product.ProductRepository;
import com.ecommerce.api.utility.EnumConstants;
import com.ecommerce.api.utility.NotFoundException;
import com.ecommerce.api.cart.model.Cart;
import com.ecommerce.api.cart.service.CartRepository;
import com.ecommerce.api.order_item.RequestOrderItemDto;
import com.ecommerce.api.order_timeline.OrderTimeline;
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

    public OrderResponse placeOrder(OrderRequest orderRequest){
        Cart cart = cartRepository.findById(orderRequest.getCartId())
                .orElseThrow(()-> new NotFoundException(orderRequest.getCartId(),
                        Cart.class.getSimpleName()));

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

        return modelMapper.map(createdOrder, OrderResponse.class);
    }

    public OrderResponse addToOrder(RequestOrderItemDto requestOrderItemDto){
        Order order = orderRepository.findById(requestOrderItemDto.getOrderId())
                .orElseThrow(()-> new NotFoundException(requestOrderItemDto.getOrderItemId(),
                        Order.class.getSimpleName()));
        Product product = productRepository.findById(requestOrderItemDto.getProductId())
                .orElseThrow(()-> new NotFoundException(requestOrderItemDto.getProductId(),
                        Product.class.getSimpleName()));

        // create a new orderItem
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId( null );
        orderItem.setUnitPrice( product.getPrice() );
        orderItem.setTotalPrice( (long) product.getPrice()
                * requestOrderItemDto.getItemQuantity() );
        orderItem.setProduct( product );
        orderItem.setItemQuantity( requestOrderItemDto.getItemQuantity() );
        orderItem.setOrder( order );

        // update the order info
        order.setTotalQuantity( order.getTotalQuantity() + orderItem.getItemQuantity() );
        order.setTotalPrice( order.getTotalPrice() + orderItem.getTotalPrice() );
        order.getOrderItems().add( orderItem );
        return modelMapper.map( orderRepository.save( order ), OrderResponse.class );
    }

    public OrderResponse getOrderById(Long orderId ){
        Order order = orderRepository.findById( orderId )
                .orElseThrow( ()->new NotFoundException(orderId,
                        Order.class.getSimpleName()) );
        return modelMapper.map( order, OrderResponse.class );
    }

    public List<OrderResponse> getAllOrder(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map( order -> modelMapper.map(order,
                OrderResponse.class) ).toList();
    }

    public void deleteOrder( Long orderId ){
        Order order = orderRepository.findById( orderId )
                .orElseThrow( ()-> new NotFoundException( orderId,
                        Order.class.getSimpleName() ) );
        orderRepository.deleteById( orderId );
    }

    // admin only
    public OrderResponse approveOrder(Long orderId ){
        Order order = orderRepository.findById( orderId )
                .orElseThrow( ()-> new NotFoundException( orderId,
                        Order.class.getSimpleName()) );

        List<OrderItem> orderItems = order.getOrderItems();

        List<OrderItem> list = orderItems.stream().map(orderItem -> {
            Product product = new Product();
            product = orderItem.getProduct();
            product.setInStock(product.getInStock() - orderItem.getItemQuantity());
            productRepository.save(product);
            return orderItem;
        }).toList();

        order.setOrderStatus( EnumConstants.OderStatus.APPROVE );
        order.getOrderTimeline().setOrderApproveDateTime(LocalDateTime.now());
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }

    public OrderResponse updateReadyToDeliver(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId,
                        Order.class.getSimpleName()));
        order.getOrderTimeline().setReadyToDeliverDateTime(LocalDateTime.now());
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }

    public OrderResponse cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId,
                        Order.class.getSimpleName()));
        order.setOrderStatus(EnumConstants.OderStatus.CANCELED);

        List<OrderItem> orderItems = order.getOrderItems();

        List<OrderItem> list = orderItems.stream().map(orderItem -> {
            Product product = new Product();
            product = orderItem.getProduct();
            product.setInStock(product.getInStock() + orderItem.getItemQuantity());
            productRepository.save(product);
            return orderItem;
        }).toList();

        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }

    public OrderResponse updateDeliveryDateOfOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new NotFoundException(orderId,
                        Order.class.getSimpleName()));
        order.setOrderStatus(EnumConstants.OderStatus.DELIVERED);
        order.getOrderTimeline().setDeliveryDateTime(LocalDateTime.now());
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }
}
