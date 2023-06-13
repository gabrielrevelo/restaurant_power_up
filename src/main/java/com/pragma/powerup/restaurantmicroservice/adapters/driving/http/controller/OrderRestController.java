package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.configuration.response.SuccessfulApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders/")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderHandler orderHandler;

    @PostMapping()
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<SuccessfulApiResponse<Void>> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        orderHandler.createOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.ORDER_CREATED_MESSAGE));
    }

    @GetMapping()
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> listOrders(
            @RequestParam OrderStatus status,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber - 1);
        List<OrderResponseDto> orders = orderHandler.listOrders(status, pageable);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(orders));
    }

    @PatchMapping("/{idOrder}/assign")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> assignOrder(
            @PathVariable Long idOrder) {
        orderHandler.assignOrder(idOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>("Order assigned successfully"));
    }

    @PatchMapping("/{idOrder}/ready")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> orderReady(
            @PathVariable Long idOrder) {
        orderHandler.orderReady(idOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>("Order ready successfully"));
    }
}
