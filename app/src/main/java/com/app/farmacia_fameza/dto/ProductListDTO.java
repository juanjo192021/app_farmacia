package com.app.farmacia_fameza.dto;

import java.io.Serializable;

public class ProductListDTO implements Serializable {
    private Integer id;
    private String name;
    private Integer stock_actual;
    private Double unit_price;


    public ProductListDTO() {
        this.id = 0;
        this.name = "name";
        this.stock_actual = 0;
        this.unit_price = 0.0;
    }

    public ProductListDTO(Integer id, String name, Integer stock_actual, Double unit_price) {
        this.id = id;
        this.name = name;
        this.stock_actual = stock_actual;
        this.unit_price = unit_price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
