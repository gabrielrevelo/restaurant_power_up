package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestaurantResponseDto {
    private String name;
    private String address;
    private String idOwner;
    private String phone;
    private String urlLogo;
    private String nit;
}
