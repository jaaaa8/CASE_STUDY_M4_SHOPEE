package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Product;
import com.example.case_study_mdl_4_shopee.repository.IProductRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ISellerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerProductService implements ISellerProductService {
    // THÊM VÀO SellerProductService


    public List<Product> getMyProducts() {
        Long sellerId = getCurrentSellerId();
        return getMyProducts(sellerId);
    }

    public Product createProduct(Product product) {
        Long sellerId = getCurrentSellerId();
        return create(product, sellerId);
    }

    public Product getMyProductById(Long id) {
        Long sellerId = getCurrentSellerId();
        return getById(id, sellerId);
    }

    public Product updateProduct(Long id, Product product) {
        Long sellerId = getCurrentSellerId();
        return update(id, product, sellerId);
    }

    private Long getCurrentSellerId() {
        throw new RuntimeException("Implement getCurrentSellerId() based on your login/session");
    }

    private final IProductRepository productRepository;

    @Override
    public List<Product> getMyProducts(Long sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    @Override
    public Product create(Product product, Long sellerId) {
        product.setSellerId(sellerId); // sửa theo field thực tế của Product
        if (product.getStock() == null) product.setStock(0);
        return productRepository.save(product);
    }

    @Override
    public Product getById(Long productId, Long sellerId) {
        return productRepository.findByProductIdAndSellerId(productId, sellerId)
                .orElseThrow(() -> new RuntimeException("Product not found or forbidden"));
    }

    @Override
    public Product update(Long productId, Product input, Long sellerId) {
        Product p = getById(productId, sellerId);

        p.setName(input.getName());
        p.setDescription(input.getDescription());
        p.setImageUrl(input.getImageUrl());
        p.setPrice(input.getPrice());
        p.setStock(input.getStock());
        p.setCategoryId(input.getCategoryId()); // hoặc setCategory(...)

        return productRepository.save(p);
    }

    @Override
    public void delete(Long productId, Long sellerId) {
        Product p = getById(productId, sellerId);
        productRepository.delete(p);
    }
}