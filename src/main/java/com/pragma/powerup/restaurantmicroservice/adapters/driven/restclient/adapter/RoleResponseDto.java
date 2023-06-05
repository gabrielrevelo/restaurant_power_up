package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleResponseDto {
    private Long id;
    private String name;
    private String description;
}
