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
@Schema(description = "Dish DTO")
public class DishRequestDto {
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Name", example = "Hamburguesa")
    private String name;
    @NotNull(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Category", example = "1", type = "Long")
    private Long category;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Description", example = "Hamburguesa con queso")
    private String description;
    @NotNull(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Price", example = "15000")
    private Double price;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "URL Image", example = "https://ejemplo.com/image.png")
    private String urlImage;
    @NotNull(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "ID Restaurant", example = "1", type = "Long")
    private Long idRestaurant;
}
