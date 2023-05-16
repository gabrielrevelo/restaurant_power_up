package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Dish Update DTO")
public class DishUpdateDto {
    @NotNull(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Price", example = "30000")
    private Double price;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Description", example = "Hamburguesa con queso")
    private String description;
}
