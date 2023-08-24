package com.michael.springbootmall.service;

import com.michael.springbootmall.dto.ProductQueryParams;
import com.michael.springbootmall.dto.ProductRequest;
import com.michael.springbootmall.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    Product getProductById(Integer Id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer id,ProductRequest productRequest);

    void deleteProduct(Integer id);

    List<Product> getProducts(ProductQueryParams QueryParams);
}
