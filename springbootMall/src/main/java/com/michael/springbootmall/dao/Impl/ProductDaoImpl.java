package com.michael.springbootmall.dao.Impl;

import com.michael.springbootmall.dao.ProductDao;
import com.michael.springbootmall.dto.ProductRequest;
import com.michael.springbootmall.model.Product;
import com.michael.springbootmall.rowmapper.ProductRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate NP;

    @Override
    public Product getProductById(Integer Id) {

        String sql = "select product_id,product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "from product where product_id=:Id";

        Map<String, Object> map = new HashMap<>();
        map.put("Id",Id);

        List<Product> productList = NP.query(sql, map, new ProductRowmapper());

        if (productList.size()>0){
            return productList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "insert into product(product_name, category, " +
                "image_url, price, stock, description, created_date, " +
                "last_modified_date) values (:productName, :category, " +
                ":imageUrl, :price, :stock, :description, :createdDate, " +
                ":lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NP.update(sql,new MapSqlParameterSource(map),keyHolder);

        int productId = keyHolder.getKey().intValue();
        return  productId;

    }
}
