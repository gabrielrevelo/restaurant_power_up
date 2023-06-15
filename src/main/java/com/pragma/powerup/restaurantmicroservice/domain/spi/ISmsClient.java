package com.pragma.powerup.restaurantmicroservice.domain.spi;

public interface ISmsClient {
    void sendSms(String phone, String securityCode, String token);
}
