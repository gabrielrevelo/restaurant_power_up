package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

/**
 * Exception to throw when an order does not belong to the client who is trying to access it.
 */
public class OrderNotBelongClientException extends RuntimeException {
    public OrderNotBelongClientException() {
        super();
    }
}
