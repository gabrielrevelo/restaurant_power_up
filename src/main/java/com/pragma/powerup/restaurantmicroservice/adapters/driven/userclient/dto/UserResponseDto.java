package com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String dniNumber;
    private String dateOfBirth;
}
