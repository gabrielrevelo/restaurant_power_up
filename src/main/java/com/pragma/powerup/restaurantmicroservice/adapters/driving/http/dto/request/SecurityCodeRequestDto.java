package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Security Code DTO")
public class SecurityCodeRequestDto {
    private String securityCode;
}
