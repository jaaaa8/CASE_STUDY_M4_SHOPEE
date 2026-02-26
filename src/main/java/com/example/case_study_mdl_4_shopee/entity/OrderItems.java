package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemsId;
    @ManyToOne
    @JoinColumn(name = "subOrderId")
    private SubOrders subOrders;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    private int quantity;
    private int price;
    private boolean isChosen = true;

}
