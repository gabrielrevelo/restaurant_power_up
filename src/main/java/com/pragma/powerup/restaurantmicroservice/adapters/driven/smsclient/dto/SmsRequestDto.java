package com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SmsRequestDto {
    private String phone;
    private String securityCode;
}
