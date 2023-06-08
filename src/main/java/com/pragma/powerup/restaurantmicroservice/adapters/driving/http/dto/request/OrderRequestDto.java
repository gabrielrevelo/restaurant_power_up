package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Employee Request DTO")
@ToString
public class OrderRequestDto {
    private Long idRestaurant;
    private List<MenuSelection> menuSelections;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class MenuSelection {
        private Long idDish;
        private Integer quantity;
    }
}
