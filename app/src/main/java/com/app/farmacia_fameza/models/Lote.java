package com.app.farmacia_fameza.models;

public class Lote {
    private Integer id;
    private String lote_number;
    private String expiration_date;
    private String production_date;
    private String alert_date;
    private Integer quantity;

    public Lote(String alert_date, String expiration_date, Integer id, String lote_number, String production_date, Integer quantity) {
        this.alert_date = alert_date;
        this.expiration_date = expiration_date;
        this.id = id;
        this.lote_number = lote_number;
        this.production_date = production_date;
        this.quantity = quantity;
    }

    public Lote() {
        this.alert_date = "alert_date";
        this.expiration_date = "expiration_date";
        this.id = 0;
        this.lote_number = "lote_number";
        this.production_date = "production_date";
        this.quantity = 0;
    }

    public String getAlert_date() {
        return alert_date;
    }

    public void setAlert_date(String alert_date) {
        this.alert_date = alert_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLote_number() {
        return lote_number;
    }

    public void setLote_number(String lote_number) {
        this.lote_number = lote_number;
    }
}
