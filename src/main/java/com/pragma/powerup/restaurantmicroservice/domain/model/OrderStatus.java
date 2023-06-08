package com.pragma.powerup.restaurantmicroservice.domain.model;

public enum OrderStatus {
    PENDING("Pending"),
    IN_PREPARATION("In Preparation"),
    READY("Ready"),
    DELIVERED("Delivered");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
