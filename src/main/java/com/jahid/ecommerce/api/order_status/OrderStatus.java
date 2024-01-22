package com.jahid.ecommerce.api.order_status;

import com.jahid.ecommerce.api.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_status_id", nullable = false)
    private Long order_status_id;

    @Column(name = "order_place_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderPlaceDateTime;

    @Column(name = "order_approve_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderApproveDateTime;

    @Column(name = "ready_to_deliver_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date readyToDeliverDateTime;

    @Column(name = "delivery_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDateTime;

    @OneToOne(mappedBy = "orderStatus")
    private Order order;
}
