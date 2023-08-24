package com.michael.springbootmall.dto;

public class ProductQueryParams {

    private String category;
    private String search;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
