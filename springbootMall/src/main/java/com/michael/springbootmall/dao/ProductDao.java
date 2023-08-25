package com.michael.springbootmall.dao;

import com.michael.springbootmall.dto.ProductQueryParams;
import com.michael.springbootmall.dto.ProductRequest;
import com.michael.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    Product getProductById(Integer Id);

    Integer createProduct(ProductRequest productRequest);

    void  updateProduct(Integer id,ProductRequest productRequest);

    void deleteProduct(Integer id);

    List<Product> getProducts(ProductQueryParams QueryParams);

    Integer countProduct(ProductQueryParams QueryParams);

    void updateStock(Integer productId,Integer stock);
}
