package com.app.farmacia_fameza.models;

import java.util.Date;

public class User {
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Date  date_birth;
    private String cell_phone;
    private Role role;
    private Integer status;

    public User() {
        this.id = 0;
        this.first_name = "first_name";
        this.last_name = "last_name";
        this.email = "email";
        this.password = "password";
        this.date_birth = new Date();
        this.role = new Role();
        this.cell_phone = "cell_phone";
        this.status = 0;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String first_name, String last_name, String email, String password, Date date_birth, String cell_phone, Role role, Integer status) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.date_birth = date_birth;
        this.cell_phone = cell_phone;
        this.role = role;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
