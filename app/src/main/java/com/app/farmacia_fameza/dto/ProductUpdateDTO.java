package com.app.farmacia_fameza.dto;

public class ProductUpdateDTO {
    private String name, description, image, brand, category;
    private Double unit_price;
    private Integer id,status;

    public ProductUpdateDTO() {
    }

    public ProductUpdateDTO(String name, String description, String image, String brand, String category, Double unit_price, Integer id, Integer status) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.brand = brand;
        this.category = category;
        this.unit_price = unit_price;
        this.id = id;
        this.status = status;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
