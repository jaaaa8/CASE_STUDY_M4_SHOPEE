package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "location")
    private List<City> cities;
}
