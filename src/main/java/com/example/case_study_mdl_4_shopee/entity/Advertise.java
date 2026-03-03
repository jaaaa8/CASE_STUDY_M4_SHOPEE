package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.AdvertiseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "advertise",
        indexes = {
                @Index(name = "idx_ad_product", columnList = "product_id"),
                @Index(name = "idx_ad_status", columnList = "status")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertiseId")
    private Integer advertiseId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    // admin duyệt quảng cáo, nên để nullable = false vì chỉ có admin mới có quyền tạo quảng cáo, nên sẽ luôn có adminId
    @ManyToOne
    @JoinColumn(name = "adminId", nullable = false)
    private Account admin;

    @Column(name = "startTime", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "endTime", nullable = false)
    private LocalDateTime endTime;

    // giá quảng cáo tính theo giờ
    @Column(name = "pricePerHour", nullable = false)
    private Integer pricePerHour;

    private Integer totalHours;

    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AdvertiseStatus status = AdvertiseStatus.PENDING;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;
}
