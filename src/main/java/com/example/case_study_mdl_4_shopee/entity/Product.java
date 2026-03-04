package com.example.case_study_mdl_4_shopee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private Long stock;
    @Builder.Default
    @Column(nullable = false)
    private Long sold = 0L;
    private String imageUrl;
    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Account seller;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
}
