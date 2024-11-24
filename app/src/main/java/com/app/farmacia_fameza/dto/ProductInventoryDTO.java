package com.app.farmacia_fameza.dto;

public class ProductInventoryDTO {
    private String sku;
    private String nameProduct;
    private String fecha;
    private String detalle;
    private Integer entrada;
    private Integer salida;
    private Integer saldo;
    private Double precio;

    public ProductInventoryDTO(String sku, String nameProduct, String fecha, String detalle, Integer entrada, Integer salida, Integer saldo, Double precio) {
        this.sku = sku;
        this.nameProduct = nameProduct;
        this.fecha = fecha;
        this.detalle = detalle;
        this.entrada = entrada;
        this.salida = salida;
        this.saldo = saldo;
        this.precio = precio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getEntrada() {
        return entrada;
    }

    public void setEntrada(Integer entrada) {
        this.entrada = entrada;
    }

    public Integer getSalida() {
        return salida;
    }

    public void setSalida(Integer salida) {
        this.salida = salida;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
