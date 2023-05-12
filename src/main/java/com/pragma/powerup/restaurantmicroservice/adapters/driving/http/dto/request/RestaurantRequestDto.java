package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
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
public class RestaurantRequestDto {
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z\\d]+$", message = Constants.INVALID_FORMAT_MESSAGE)
    private String name;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    private String address;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "\\d*", message = Constants.INVALID_FORMAT_MESSAGE)
    private String idOwner;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "^\\+?[0-9]{1,12}$", message = Constants.INVALID_FORMAT_MESSAGE)
    private String phone;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    private String urlLogo;
    @NotEmpty(message = Constants.EMPTY_FIELD_MESSAGE)
    @Pattern(regexp = "\\d*", message = Constants.INVALID_FORMAT_MESSAGE)
    private String nit;
}
