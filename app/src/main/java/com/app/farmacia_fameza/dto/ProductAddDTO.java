package com.app.farmacia_fameza.dto;

import com.app.farmacia_fameza.models.Brand;
import com.app.farmacia_fameza.models.Category;

public class ProductAddDTO {
    private int id;
    private String sku, name, description, image;
    private int brand, category;
    private Double unit_price;

    public ProductAddDTO() {

    }

    public ProductAddDTO(int id,String sku, String name, String description, String image, Double unit_price, int brand, int category) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.image = image;
        this.unit_price = unit_price;
        this.brand = brand;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getBrand() {
        return brand;
    }

    public void setBrand(int brand) {
        this.brand = brand;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }
}
