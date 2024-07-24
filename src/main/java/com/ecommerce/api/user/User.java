package com.ecommerce.api.user;

import com.ecommerce.api.order.model.Order;
import com.ecommerce.api.utility.EnumConstants;
import com.ecommerce.api.cart.model.Cart;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "mobile_no", nullable = false, unique = true)
    private String mobileNo;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @Column(name = "email")
    private String email;

    @Column(name = "address", length = 2000)
    private String address;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'CUSTOMER'", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumConstants.UserRole userRole = EnumConstants.UserRole.CUSTOMER;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orderSet;
}
