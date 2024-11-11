package com.app.farmacia_fameza.dto;

public class ProductOutputDetailDTO {
    private String SKU;
    private Integer quantity;

    public ProductOutputDetailDTO() {
        this.SKU = "SKU";
        this.quantity = 0;
    }

    public ProductOutputDetailDTO(String SKU, Integer quantity) {
        this.SKU = SKU;
        this.quantity = quantity;
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
}
