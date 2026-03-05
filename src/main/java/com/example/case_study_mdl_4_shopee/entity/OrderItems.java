package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_orderitems_suborder", columnList = "subOrderId"),
        @Index(name = "idx_orderitems_product", columnList = "productId")
})
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderItemsId")
    private Long orderItemsId;
    @ManyToOne
    @JoinColumn(name = "subOrderId", nullable = false)
    private SubOrders subOrders;
    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private Long price;
    private boolean isChosen = true;

}
