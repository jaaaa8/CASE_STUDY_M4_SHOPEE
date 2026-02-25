package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.entity.enums.SubOrderStatus;
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
    private Long sub_order_id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Account seller;
    @ManyToOne
    @JoinColumn(name = "confirmed_by")
    private Account confirmedBy;
    @Enumerated(EnumType.STRING)
    private SubOrderStatus sub_order_status;
    private int total;

    @OneToMany(mappedBy = "subOrders")
    private List<OrderItems> orderItems;
}
