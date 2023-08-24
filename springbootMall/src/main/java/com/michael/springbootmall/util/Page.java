package com.michael.springbootmall.util;

import com.michael.springbootmall.model.Product;

import java.util.List;

public class Page<T> {
    private Integer limit;
    private Integer offset;
    private Integer total;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getProduct() {
        return product;
    }

    public void setProduct(List<T> product) {
        this.product = product;
    }

    private List<T> product;

}
