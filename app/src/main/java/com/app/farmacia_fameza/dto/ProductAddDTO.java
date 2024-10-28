package com.app.farmacia_fameza.dto;

import com.app.farmacia_fameza.models.Brand;
import com.app.farmacia_fameza.models.Category;

public class ProductAddDTO {
    private String sku, name, description, image, brand, category;
    private Double unit_price;

    public ProductAddDTO() {}

    public ProductAddDTO(String sku, String name, String description, String image, Double unit_price, String brand, String category) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.image = image;
        this.unit_price = unit_price;
        this.brand = brand;
        this.category = category;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }
}
