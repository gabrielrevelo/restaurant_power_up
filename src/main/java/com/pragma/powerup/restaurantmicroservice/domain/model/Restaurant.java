package com.pragma.powerup.restaurantmicroservice.domain.model;

import java.util.Objects;

public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String idOwner;
    private String phone;
    private String urlLogo;
    private String nit;

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public Restaurant(Long id, String name, String address, String idOwner, String phone, String urlLogo, String nit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.idOwner = idOwner;
        this.phone = phone;
        this.urlLogo = urlLogo;
        this.nit = nit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getIdOwner(), that.getIdOwner()) && Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getUrlLogo(), that.getUrlLogo()) && Objects.equals(getNit(), that.getNit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAddress(), getIdOwner(), getPhone(), getUrlLogo(), getNit());
    }
}
