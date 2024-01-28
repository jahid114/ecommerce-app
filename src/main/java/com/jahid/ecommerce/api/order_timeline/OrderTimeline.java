package com.jahid.ecommerce.api.order_timeline;

import com.jahid.ecommerce.api.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_timeline")
public class OrderTimeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_timeline_id", nullable = false)
    private Long orderTimelineId;

    @Column(name = "order_place_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderPlaceDateTime;

    @Column(name = "order_approve_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderApproveDateTime;

    @Column(name = "ready_to_deliver_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime readyToDeliverDateTime;

    @Column(name = "delivery_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deliveryDateTime;

    @OneToOne(mappedBy = "orderTimeline")
    private Order order;
}
