package com.example.case_study_mdl_4_shopee.entity;

import com.example.case_study_mdl_4_shopee.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;
    private String phone;
    private String address;
    private Date createdAt;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToMany(mappedBy = "customerOrder")
    private List<Orders> orders;

    @OneToMany(mappedBy = "customerReview")
    private List<Review> reviews;
}
