package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    @Column(unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;
}
