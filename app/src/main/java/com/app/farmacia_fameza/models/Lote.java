package com.app.farmacia_fameza.models;

public class Lote {
    private Integer id;
    private String lote_number;
    private String expiration_date;
    private Integer quantity;
    private Product product;
    private Supplier supplier;

    public Lote() {
        this.id = 0;
        this.lote_number = "lote_number";
        this.expiration_date = "expiration_date";
        this.quantity = 0;
        this.product = new Product();
        this.supplier = new Supplier();
    }

    public Lote(Integer id, String lote_number, String expiration_date, Product product, Integer quantity, Supplier supplier) {
        this.id = id;
        this.lote_number = lote_number;
        this.expiration_date = expiration_date;
        this.product = product;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getLote_number() {
        return lote_number;
    }

    public void setLote_number(String lote_number) {
        this.lote_number = lote_number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void updateProductStock() {
        if (product != null && quantity != null) {
            // Sumar la cantidad del lote al stock actual del producto
            Integer newStock = product.getStock_actual() + quantity;
            product.setStock_actual(newStock);
        }
    }
}
