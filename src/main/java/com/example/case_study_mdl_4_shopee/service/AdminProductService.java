package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.dto.ProductForAdminDto;
import com.example.case_study_mdl_4_shopee.entity.Product;
import com.example.case_study_mdl_4_shopee.repository.IProductRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminProductService implements IAdminProductService {

    private final IProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AdminProductService(IProductRepository productRepository,
                               ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductForAdminDto> listProduct() {
        return productRepository.findAll()
                .stream()
                .map(p -> new ProductForAdminDto(
                        p.getProductId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getStock(),
                        p.getImageUrl(),
                        p.getCreatedAt(),
                        p.getCategory() != null ? p.getCategory().getName() : null,
                        p.getSeller() != null ? p.getSeller().getUsername() : null,
                        null
                ))
                .toList();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }
    @Override
    public ProductForAdminDto findDtoById(int id) {
        Product p = productRepository.findById((int) id).orElse(null);

        if (p == null) return null;

        return new ProductForAdminDto(
                p.getProductId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                p.getImageUrl(),
                p.getCreatedAt(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getSeller() != null ? p.getSeller().getUsername() : null,
                null
        );
    }

    @Override
    public boolean forceDeleteProduct(int productId) {
        try {
            productRepository.deleteById(productId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<ProductForAdminDto> search(String name,
                                           String shopName,
                                           Integer categoryId) {

        name = (name == null || name.trim().isEmpty()) ? null : name;
        shopName = (shopName == null || shopName.trim().isEmpty()) ? null : shopName;

        return productRepository.search(name, shopName, categoryId)
                .stream()
                .map(product -> modelMapper.map(product, ProductForAdminDto.class))
                .toList();
    }

}
