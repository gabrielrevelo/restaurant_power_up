package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Employee Request DTO")
@ToString
public class OrderResponseDto {
    private Long id;
    private Long idClient;
    private Long idRestaurant;
    private Long idChef;
    private LocalDate date;
    private OrderStatus status;
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
