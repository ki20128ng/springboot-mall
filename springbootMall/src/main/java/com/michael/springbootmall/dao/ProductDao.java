package com.michael.springbootmall.dao;

import com.michael.springbootmall.dto.ProductRequest;
import com.michael.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer Id);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer id,ProductRequest productRequest);
}
