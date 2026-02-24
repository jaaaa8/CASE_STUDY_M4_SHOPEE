package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.entity.enums.SubOrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SubOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sub_order_id;
    private Long order_id;
    private Long confirmed_by;
    private Long seller_id;
    @Enumerated(EnumType.STRING)
    private SubOrderStatus sub_order_status;
    private int total;

}
