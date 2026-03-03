package com.example.case_study_mdl_4_shopee.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
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
    private Long ordersId;
    @ManyToOne
    @JoinColumn(name = "customerId")
    private Account customerOrder;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private int total;
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order")
    private List<SubOrders> subOrders;
}
