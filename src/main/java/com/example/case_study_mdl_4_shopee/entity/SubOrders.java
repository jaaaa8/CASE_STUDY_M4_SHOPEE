package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_suborder_order", columnList = "orderId"),
        @Index(name = "idx_suborder_seller", columnList = "sellerId"),
        @Index(name = "idx_suborder_shipper", columnList = "shipperId")
})
public class SubOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subOrderId")
    private Long subOrderId;
    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    @JsonIgnore
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "sellerId", nullable = false)
    @JsonIgnore
    private Account sellerOrder;
    @ManyToOne
    @JoinColumn(name = "shipperId")
    @JsonIgnore
    private Account shipper;
    @ManyToOne
    @JoinColumn(name = "confirmedBy")
    private Account confirmedBy;
    @ManyToOne
    @JoinColumn(name = "warehouseId")
    private Warehouse warehouse;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SubOrderStatus status = SubOrderStatus.PENDING;
    @Column(nullable = false)
    @Builder.Default
    private Long total = 0L;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "subOrders")
    private List<OrderItems> orderItems;
}
