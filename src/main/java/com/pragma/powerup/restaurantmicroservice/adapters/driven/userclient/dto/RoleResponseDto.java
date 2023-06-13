package com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleResponseDto {
    private Long id;
    private String name;
    private String description;
}
