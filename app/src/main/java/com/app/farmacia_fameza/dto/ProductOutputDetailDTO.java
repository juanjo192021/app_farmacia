package com.app.farmacia_fameza.dto;

public class ProductOutputDetailDTO {
    private int id;
    private String SKU;
    private Integer quantity;
    private Double priceHistory;

    public ProductOutputDetailDTO() {
        this.id = 0;
        this.SKU = "SKU";
        this.quantity = 0;
        this.priceHistory = 0.0;
    }

    public ProductOutputDetailDTO(int id,String SKU, Integer quantity, Double priceHistory) {
        this.id = id;
        this.SKU = SKU;
        this.quantity = quantity;
        this.priceHistory = priceHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(Double priceHistory) {
        this.priceHistory = priceHistory;
    }
}
