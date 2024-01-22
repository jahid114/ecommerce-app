package com.jahid.ecommerce.api.order_item;

import com.jahid.ecommerce.api.cart.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id", nullable = false)
    private Long order_item_id;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @ManyToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;
}
