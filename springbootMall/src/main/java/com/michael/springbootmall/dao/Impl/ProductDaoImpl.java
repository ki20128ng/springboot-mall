package com.michael.springbootmall.dao.Impl;

import com.michael.springbootmall.dao.ProductDao;
import com.michael.springbootmall.dto.ProductQueryParams;
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
    public Integer countProduct(ProductQueryParams QueryParams) {
        String sql = "select count(*) from product where 1=1";

        Map<String, Object> map = new HashMap<>();
        if(QueryParams.getCategory() != null){
            sql = sql + " and category = :productCategory";
            map.put("productCategory",QueryParams.getCategory());
        }

        if(QueryParams.getSearch() != null){
            sql = sql + " and product_name like :search";
            map.put("search","%"+QueryParams.getSearch()+"%");
        }

        Integer total = NP.queryForObject(sql,map,Integer.class);

        return total;
    }

    @Override
    public List<Product> getProducts(ProductQueryParams QueryParams) {
        String sql = "select product_id,product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "from product where 1=1";

        Map<String, Object> map = new HashMap<>();
        if(QueryParams.getCategory() != null){
            sql = sql + " and category = :productCategory";
            map.put("productCategory",QueryParams.getCategory());
        }

        if(QueryParams.getSearch() != null){
            sql = sql + " and product_name like :search";
            map.put("search","%"+QueryParams.getSearch()+"%");
        }

        sql = sql + " order by " + QueryParams.getOrderBy() + " " + QueryParams.getSort();
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit",QueryParams.getLimit());
        map.put("offset",QueryParams.getOffset());

        List<Product> productList = NP.query(sql,map,new ProductRowmapper());

        return productList;

    }

    @Override
    public void deleteProduct(Integer id) {
        String sql = "delete from product where product_id=:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",id);

        NP.update(sql,map);
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

    @Override
    public void updateProduct(Integer id, ProductRequest productRequest) {
        String sql = "update product set product_name=:productName, " +
                "category=:category, image_url=:imageUrl, price=:price, " +
                "stock=:stock, description=:description, " +
                "last_modified_date=:lastModifiedDate " +
                "where product_id=:productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",id);
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();
        map.put("lastModifiedDate",now);

        NP.update(sql,map);
    }
}
