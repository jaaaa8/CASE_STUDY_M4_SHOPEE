package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Review;

public interface IReviewService {
    void addReview(Long customerId, Long productId, String comments, int rating, String imageUrl);
}