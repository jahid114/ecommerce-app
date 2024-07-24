package com.ecommerce.api.order.model;

import com.ecommerce.api.order_item.model.OrderItem;
import com.ecommerce.api.utility.EnumConstants;
import com.ecommerce.api.order_timeline.OrderTimeline;
import com.ecommerce.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "order_status",columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumConstants.OderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "order_timeline_id")
    private OrderTimeline orderTimeline;

}
