package com.pragma.powerup.restaurantmicroservice.configuration.response;

public class ErrorApiResponse extends CustomApiResponse<Void> {
    public ErrorApiResponse(String errorMessage) {
        super(false, errorMessage, null);
    }
}