package com.app.farmacia_fameza.dto;

public class ItemListDTO {

    private Integer id;
    private String name;
    private Integer status;
    private Integer count_Product;

    public ItemListDTO() {
        this.id = 0;
        this.count_Product = 0;
        this.status = 0;
        this.name = "name";
    }

    public ItemListDTO(Integer id, Integer count_Product, String name, Integer status) {
        this.id = id;
        this.count_Product = count_Product;
        this.name = name;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount_Product() {
        return count_Product;
    }

    public void setCount_Product(Integer count_Product) {
        this.count_Product = count_Product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
