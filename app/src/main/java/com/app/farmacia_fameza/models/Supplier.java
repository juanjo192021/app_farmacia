package com.app.farmacia_fameza.models;

public class Supplier {
    private Integer id;
    private String name;
    private String contact_phone;
    private String email;

    public Supplier() {
        this.id = 0;
        this.name = "name";
        this.contact_phone = "contact_phone";
        this.email = "email";
    }

    public Supplier(Integer id, String name, String contact_phone, String email) {
        this.id = id;
        this.name = name;
        this.contact_phone = contact_phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
