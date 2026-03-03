package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "discount",
        indexes = {
                @Index(name = "idx_discount_code", columnList = "code"),
                @Index(name = "idx_discount_date", columnList = "start_date, end_date")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discountId")
    private Integer discountId;

    // mã giảm giá, phải là duy nhất và không được null
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    // loại giảm giá (theo phần trăm hoặc số tiền)
    @Enumerated(EnumType.STRING)
    @Column(name = "discountType", nullable = false)
    private DiscountType discountType;

    // giá trị giảm (có thể là số tiền hoặc phần trăm tùy loại)
    @Column(name = "discountValue", nullable = false)
    private Integer discountValue;

    // giá trị đơn hàng tối thiểu để áp dụng voucher
    @Builder.Default
    @Column(name = "minOrderValue")
    private Integer minOrderValue = 0;

    // giảm tối đa (%)
    private Integer maxDiscount;

    // số lượng voucher có thể sử dụng
    private Integer usageLimit;

    // số lượng voucher đã sử dụng
    @Builder.Default
    private Integer usedCount = 0;

    // ngày bắt đầu và kết thúc của voucher
    @Column(name = "startDate")
    private LocalDateTime startDate;

    @Column(name = "endDate")
    private LocalDateTime endDate;

    // admin tạo voucher discount
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Account createdBy;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;


}
