package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

public class UserNotOwnerException extends RuntimeException{
    public UserNotOwnerException() {
        super();
    }

    public UserNotOwnerException(String message) {
        super(message);
    }
}
