package com.app.farmacia_fameza.dto;

public class ProductEntryDetailDTO {
    private Integer id;
    private String SKU;
    private Integer quantity;
    private String expiration_date;
    private String production_date;
    private String alert_date;
    private Double priceHistory;

    public ProductEntryDetailDTO() {
        this.id = 0;
        this.SKU = "SKU0000";
        this.quantity = 0;
        this.expiration_date = "expiration_date";
        this.production_date = "production_date";
        this.alert_date = "alert_date";
        this.priceHistory = 0.0;
    }

    public ProductEntryDetailDTO(int id,String SKU, Integer quantity, String expiration_date, String production_date, String alert_date, Double priceHistory) {
        this.id = id;
        this.SKU = SKU;
        this.quantity = quantity;
        this.expiration_date = expiration_date;
        this.production_date = production_date;
        this.alert_date = alert_date;
        this.priceHistory = priceHistory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getAlert_date() {
        return alert_date;
    }

    public void setAlert_date(String alert_date) {
        this.alert_date = alert_date;
    }

    public Double getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(Double priceHistory) {
        this.priceHistory = priceHistory;
    }
}
