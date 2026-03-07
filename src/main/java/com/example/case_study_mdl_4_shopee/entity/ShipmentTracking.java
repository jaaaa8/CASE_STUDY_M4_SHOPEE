package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.TrackingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipmentTracking",
        indexes = {
                @Index(name = "idx_tracking_suborder", columnList = "subOrderId"),
                @Index(name = "idx_tracking_warehouse", columnList = "warehouseId"),
                @Index(name = "idx_tracking_suborder_created", columnList = "subOrderId, createdAt")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trackingId")
    private Long trackingId;

    @ManyToOne
    @JoinColumn(name = "subOrderId", nullable = false)
    private SubOrders subOrder;

    // id kho hàng để biết đơn hàng đang ở đâu
    // kho hàng có thể null lúc đầu
    @ManyToOne(optional = true)
    @JoinColumn(name = "warehouseId" , nullable = true)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TrackingStatus status;

    // admin hoặc shipper nào cập nhật trạng thái đơn hàng lần cuối
    // người cập nhật có thể null
    @ManyToOne(optional = true)
    @JoinColumn(name = "updatedBy", nullable = true)
    private Account updatedBy;

    private String note;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;
}
