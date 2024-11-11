package com.app.farmacia_fameza.dto;

public class ItemListDTO {

    private Integer id;
    private String name;
    private String status;
    private Integer count_Product;

    public ItemListDTO() {
        this.id = 0;
        this.name = "name";
        this.status = "status";
        this.count_Product = 0;
    }

    public ItemListDTO(Integer id, String name, String status, Integer count_Product) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.count_Product = count_Product;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
