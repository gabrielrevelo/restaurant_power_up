package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Dish Response DTO")
public class DishResponseDto {
    private Long id;
    private String name;
    private Long category;
    private String description;
    private Double price;
    private String urlImage;
    private Long idRestaurant;
}
