package com.michael.springbootmall.dao;

import com.michael.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(Integer Id);
}
