package com.app.farmacia_fameza.models;

import java.util.ArrayList;
import java.util.List;

public class ProductOuput {
    private Integer id;
    private String output_code;
    private String output_date;
    private User user;
    private List<Product> products;

    public ProductOuput() {
        this.id = 0;
        this.output_code = "output_code";
        this.output_date = "output_date";
        this.user = new User();
        this.products = new ArrayList<>();
    }

    public ProductOuput(String output_code, Integer id, String output_date, User user, List<Product> products) {
        this.output_code = output_code;
        this.id = id;
        this.output_date = output_date;
        this.user = user;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutput_code() {
        return output_code;
    }

    public void setOutput_code(String output_code) {
        this.output_code = output_code;
    }

    public String getOutput_date() {
        return output_date;
    }

    public void setOutput_date(String output_date) {
        this.output_date = output_date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
