package com.jahid.ecommerce.api.user;

import com.jahid.ecommerce.api.cart.Cart;
import com.jahid.ecommerce.api.order.Order;
import com.jahid.ecommerce.api.order_item.OrderItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

enum UserRole{
    CUSTOMER,
    ADMIN
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "mobile_no", nullable = false)
    private String mobile_no;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'CUSTOMER'")
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.CUSTOMER;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orderSet;
}
