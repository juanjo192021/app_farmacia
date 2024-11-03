package com.app.farmacia_fameza.models;

import java.util.ArrayList;
import java.util.List;

public class LoteEntry {
    private Integer id;
    private String number_entry;
    private String fecha_entry;
    private Supplier supplier;
    private List<Product> products;

    public LoteEntry(Integer id, String number_entry, String fecha_entry, Supplier Supplier) {
        this.id = 0;
        this.number_entry = "number_entry";
        this.fecha_entry = "fecha_entry";
        this.supplier = new Supplier();
        this.products = new ArrayList<>();
    }

    public LoteEntry(Integer id, String number_entry, Supplier supplier, String fecha_entry, List<Product> products) {
        this.id = id;
        this.number_entry = number_entry;
        this.supplier = supplier;
        this.fecha_entry = fecha_entry;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber_entry() {
        return number_entry;
    }

    public void setNumber_entry(String number_entry) {
        this.number_entry = number_entry;
    }

    public String getFecha_entry() {
        return fecha_entry;
    }

    public void setFecha_entry(String fecha_entry) {
        this.fecha_entry = fecha_entry;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
