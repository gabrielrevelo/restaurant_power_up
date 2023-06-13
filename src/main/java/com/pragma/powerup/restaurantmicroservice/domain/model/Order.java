package com.pragma.powerup.restaurantmicroservice.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private Long id;
    private Long idClient;
    private Long idRestaurant;
    private Long idChef;
    private LocalDate date;
    private OrderStatus status;
    private String phoneClient;
    private String securityCode;
    private List<MenuSelection> menuSelections;

    public Order() {
    }

    public Order(Long id, Long idClient, Long idRestaurant, Long idChef, LocalDate date, OrderStatus status, String phoneClient, String securityCode, List<MenuSelection> menuSelections) {
        this.id = id;
        this.idClient = idClient;
        this.idRestaurant = idRestaurant;
        this.idChef = idChef;
        this.date = date;
        this.status = status;
        this.phoneClient = phoneClient;
        this.securityCode = securityCode;
        this.menuSelections = menuSelections;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public Long getIdChef() {
        return idChef;
    }

    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<MenuSelection> getMenuSelections() {
        return menuSelections;
    }

    public void setMenuSelections(List<MenuSelection> menuSelection) {
        this.menuSelections = menuSelection;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getPhoneClient() {
        return phoneClient;
    }

    public void setPhoneClient(String phoneClient) {
        this.phoneClient = phoneClient;
    }

    public static class MenuSelection {
        private Long idDish;
        private Integer quantity;

        public MenuSelection() {
        }

        public MenuSelection(Long idDish, Integer quantity) {
            this.idDish = idDish;
            this.quantity = quantity;
        }

        public Long getIdDish() {
            return idDish;
        }

        public void setIdDish(Long idDish) {
            this.idDish = idDish;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
