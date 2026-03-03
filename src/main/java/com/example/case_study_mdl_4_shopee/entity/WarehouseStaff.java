package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.enums.StaffPosition;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseStaff {
    @Id
    @Column(name = "accountId")
    private Long accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "warehouseId", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffPosition position;

    @Column(name = "isActive")
    private Boolean isActive = true;

}
