package com.example.case_study_mdl_4_shopee.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordersId")
    private Long ordersId;
    @Column(unique = true)
    private String orderCode;
    @ManyToOne
    @JoinColumn(name = "customerId")
    @JsonIgnore
    private Account customerOrder;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private Long total = 0L;
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Account seller;
    @ManyToOne
    @JoinColumn(name = "discountId")
    private Discount discount;
    @Column(nullable = false)
    @Builder.Default
    private Long discountAmount = 0L; // Lưu số tiền đã giảm

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SubOrders> subOrders = new java.util.ArrayList<>();
}
