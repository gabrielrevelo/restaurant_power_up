package com.pragma.powerup.restaurantmicroservice.configuration.response;

public class SuccessfulApiResponse<T> extends CustomApiResponse<T> {
    public SuccessfulApiResponse(String message, T data) {
        super(true, message, data);
    }

    public SuccessfulApiResponse(T data) {
        super(true, null, data);
    }

    public SuccessfulApiResponse(String message) {
        super(true, message, null);
    }
}