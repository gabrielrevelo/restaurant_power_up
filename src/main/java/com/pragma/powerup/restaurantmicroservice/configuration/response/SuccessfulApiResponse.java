package com.pragma.powerup.restaurantmicroservice.configuration.response;

public class SuccessfulApiResponse<T> extends CustomApiResponse<T> {
    public SuccessfulApiResponse(String message, T data) {
        super(true, message, data, null);
    }
}