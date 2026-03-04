package com.example.case_study_mdl_4_shopee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    private Long accountId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private Long balance = 0L;
    private boolean isActive = true;
    private boolean certified = false;
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<AccountRole> accountRoles = new HashSet<>();

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    @OneToMany(mappedBy = "customerOrder")
    private List<Orders> orders;

    @OneToMany(mappedBy = "customerReview")
    private List<Review> reviews;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private WarehouseStaff warehouseStaff;

    @OneToMany(mappedBy = "sellerOrder")
    private Set<SubOrders> sellingSubOrders;

    @OneToMany(mappedBy = "shipper")
    private Set<SubOrders> shippingSubOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "updatedBy")
    private Set<ShipmentTracking> updatedTrackings = new HashSet<>();

    @OneToMany(mappedBy = "accountTransaction")
    private Set<TransactionHistory> transactions = new HashSet<>();
}
