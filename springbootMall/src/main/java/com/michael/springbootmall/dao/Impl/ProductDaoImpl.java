package com.michael.springbootmall.dao.Impl;

import com.michael.springbootmall.dao.ProductDao;
import com.michael.springbootmall.model.Product;
import com.michael.springbootmall.rowmapper.ProductRowmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
}
