package com.jahid.ecommerce.api.order_item;

import com.jahid.ecommerce.api.cart.Cart;
import com.jahid.ecommerce.api.order.Order;
import com.jahid.ecommerce.api.product.Product;
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
    private Long orderItemId;

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @ManyToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;
}
