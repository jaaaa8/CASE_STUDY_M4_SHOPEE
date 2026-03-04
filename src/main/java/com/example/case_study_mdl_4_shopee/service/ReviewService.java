package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.Product;
import com.example.case_study_mdl_4_shopee.entity.Review;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IProductRepository;
import com.example.case_study_mdl_4_shopee.repository.IReviewRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final IReviewRepository reviewRepository;
    private final IAccountRepository accountRepository;
    private final IProductRepository productRepository;

    @Override
    public void addReview(Long customerId, Long productId, String comments, int rating, String imageUrl) {
        Account customer = accountRepository.findById(customerId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        Review review = Review.builder()
                .customerReview(customer)
                .product(product)
                .comments(comments)
                .rating(rating)
                .imageUrl(imageUrl)
                .build();

        reviewRepository.save(review);
    }
}