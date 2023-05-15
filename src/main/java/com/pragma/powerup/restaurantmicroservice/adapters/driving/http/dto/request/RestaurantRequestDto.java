package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Restaurant DTO")
public class RestaurantRequestDto {
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\d\\s]+$", message = Constants.INVALID_FORMAT_MESSAGE)
    @Schema(description = "Name", example = "La Parrilla Colombiana")
    private String name;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "Address", example = "Calle 123, Bogotá")
    private String address;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "\\d*", message = Constants.INVALID_FORMAT_MESSAGE)
    @Schema(description = "ID Owner", example = "1")
    private String idOwner;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "^\\+?[0-9]{1,12}$", message = Constants.INVALID_FORMAT_MESSAGE)
    @Schema(description = "Phone", example = "+573001234567")
    private String phone;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Schema(description = "URL Logo", example = "https://ejemplo.com/logo.png")
    private String urlLogo;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "\\d*", message = Constants.INVALID_FORMAT_MESSAGE)
    @Schema(description = "NIT", example = "1234567890")
    private String nit;
}
