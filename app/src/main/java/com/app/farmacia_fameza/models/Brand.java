package com.app.farmacia_fameza.models;

import java.io.Serializable;

public class Brand implements Serializable {
    private Integer id;
    private String name;
    private Integer status;

    public Brand() {
        this.id = 0;
        this.name = "name";
        this.status = 0;
    }

    public Brand(Integer id, String name, Integer status) {
        this.id = id;
        this.name = name;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
