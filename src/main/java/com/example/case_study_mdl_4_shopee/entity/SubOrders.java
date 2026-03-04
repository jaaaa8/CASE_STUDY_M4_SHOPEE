package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subOrderId")
    private Long subOrderId;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Account sellerOrder;
    @ManyToOne
    @JoinColumn(name = "shipperId")
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

    @OneToMany(mappedBy = "subOrders")
    private List<OrderItems> orderItems;
}
