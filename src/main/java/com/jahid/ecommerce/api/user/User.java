package com.jahid.ecommerce.api.user;

import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "mobile_no", nullable = false)
    private String mobile_no;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'CUSTOMER'")
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.CUSTOMER;
}
