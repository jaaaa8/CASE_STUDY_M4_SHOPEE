package com.example.case_study_mdl_4_shopee.dto;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.Category;
import com.example.case_study_mdl_4_shopee.entity.Review;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForAdminDto {
    private Long productId;
    private String name;
    private String description;
    private Long price;
    private Long stock;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String categoryName;
    private String sellerName;
    private String reviews;

}
