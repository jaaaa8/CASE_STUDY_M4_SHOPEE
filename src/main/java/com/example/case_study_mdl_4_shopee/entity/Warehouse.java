package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouseId")
    private Long warehouseId;
    private String name;
    private String address;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;

    @OneToMany(mappedBy = "warehouse")
    private Set<WarehouseStaff> staff = new HashSet<>();
}
