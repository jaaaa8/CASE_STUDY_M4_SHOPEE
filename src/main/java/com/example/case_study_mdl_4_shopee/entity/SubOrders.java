package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SubOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subOrderId;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Account seller;
    @ManyToOne
    @JoinColumn(name = "confirmedBy")
    private Account confirmedBy;
    @Enumerated(EnumType.STRING)
    private SubOrderStatus subOrderStatus;
    private int total;

    @OneToMany(mappedBy = "subOrders")
    private List<OrderItems> orderItems;
}
