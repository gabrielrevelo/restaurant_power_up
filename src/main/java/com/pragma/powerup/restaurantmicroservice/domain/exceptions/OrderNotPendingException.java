package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

/**
 * Exception thrown when an order is not in the PENDING status.
 */
public class OrderNotPendingException extends RuntimeException {

    /**
     * Constructs an {@link OrderNotPendingException} with the specified status.
     *
     * @param status The status of the order that caused the exception
     */
    public OrderNotPendingException(String status) {
        super(status);
    }
}