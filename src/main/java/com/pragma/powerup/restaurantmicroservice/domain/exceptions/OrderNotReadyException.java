package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

public class OrderNotReadyException extends RuntimeException {
    public OrderNotReadyException(String status) {
        super(status);
    }
}