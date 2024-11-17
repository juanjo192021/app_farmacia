package com.app.farmacia_fameza.dto;

public class ProductKardexDTO {
    private int entryId;
    private String dateEntry;
    private String alertDate;
    private String expirationDate;
    private String supplierName;
    private int entryQuantity;
    private int outputQuantity;
    private int remainingQuantity;

    public ProductKardexDTO(int entryId, String dateEntry, String alertDate, String expirationDate, String supplierName, int entryQuantity, int outputQuantity, int remainingQuantity) {
        this.entryId = entryId;
        this.dateEntry = dateEntry;
        this.alertDate = alertDate;
        this.expirationDate = expirationDate;
        this.supplierName = supplierName;
        this.entryQuantity = entryQuantity;
        this.outputQuantity = outputQuantity;
        this.remainingQuantity = remainingQuantity;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(String dateEntry) {
        this.dateEntry = dateEntry;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getEntryQuantity() {
        return entryQuantity;
    }

    public void setEntryQuantity(int entryQuantity) {
        this.entryQuantity = entryQuantity;
    }

    public int getOutputQuantity() {
        return outputQuantity;
    }

    public void setOutputQuantity(int outputQuantity) {
        this.outputQuantity = outputQuantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
