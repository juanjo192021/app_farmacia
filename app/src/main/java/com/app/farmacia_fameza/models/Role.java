package com.app.farmacia_fameza.models;

public class Role {
    private Integer id;
    private String name;
    private String description;
    private Integer status;

    public Role() {
        this.id = 0;
        this.name = "name";
        this.description = "description";
        this.status = 0;
    }

    public Role(Integer id, String name, String description, Integer status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
