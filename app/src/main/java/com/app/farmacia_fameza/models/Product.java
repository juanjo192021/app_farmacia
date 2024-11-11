package com.app.farmacia_fameza.models;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer id;
    private String sku;
    private String name;
    private String description;
    private String image;
    private Integer stock_actual;
    private Double unit_price;
    private Brand brand;
    private Category category;
    private Integer status;

    public Product() {
        this.id = 0;
        this.sku = "sku";
        this.name = "name";
        this.description = "description";
        this.image = "image";
        this.stock_actual = 0;
        this.unit_price = 0.0;
        this.brand = new Brand();
        this.category = new Category();
        this.status = 0;
    }

    public Product(String sku, String name, String description, Integer id, String image, Integer stock_actual, Double unit_price, Brand brand, Category category, Integer status) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.id = id;
        this.image = image;
        this.stock_actual = stock_actual;
        this.unit_price = unit_price;
        this.brand = brand;
        this.category = category;
        this.status = status;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStock_actual() {
        return stock_actual;
    }

    public void setStock_actual(Integer stock_actual) {
        this.stock_actual = stock_actual;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }
}
