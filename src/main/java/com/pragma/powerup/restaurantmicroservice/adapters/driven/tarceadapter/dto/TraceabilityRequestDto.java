package com.pragma.powerup.restaurantmicroservice.adapters.driven.tarceadapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Traceability DTO")
public class TraceabilityRequestDto {
    private String idOrder;
    private String idClient;
    private String idEmployee;
    private String newStatus;
    private String oldStatus;
}