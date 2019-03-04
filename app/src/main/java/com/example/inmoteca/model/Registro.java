package com.example.inmoteca.model;

public class Registro {

    private String email;
    private String password;
    private String name;
    private String role;
    private String picture;

    public Registro() {
    }

    public Registro(String email, String password, String name, String role, String picture) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.picture = picture;
    }

    public Registro(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "user";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
