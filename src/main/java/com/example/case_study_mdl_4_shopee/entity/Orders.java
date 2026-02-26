package com.example.case_study_mdl_4_shopee.entity;

import java.util.Date;
import java.util.List;

import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
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
    private Date createdAt;

    @OneToMany(mappedBy = "order")
    private List<SubOrders> subOrders;
}
