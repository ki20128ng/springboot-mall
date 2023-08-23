package com.michael.springbootmall.service.Impl;

import com.michael.springbootmall.dao.ProductDao;
import com.michael.springbootmall.dto.ProductRequest;
import com.michael.springbootmall.model.Product;
import com.michael.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer id, ProductRequest productRequest) {
        productDao.updateProduct(id,productRequest);
    }

    @Override
    public Product getProductById(Integer Id) {
        return productDao.getProductById(Id);
    }
}
