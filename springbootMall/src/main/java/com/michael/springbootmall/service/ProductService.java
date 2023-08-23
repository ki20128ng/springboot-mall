package com.michael.springbootmall.service;

import com.michael.springbootmall.dto.ProductRequest;
import com.michael.springbootmall.model.Product;
import org.springframework.stereotype.Component;

@Component
public interface ProductService {
    Product getProductById(Integer Id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer id,ProductRequest productRequest);
}
