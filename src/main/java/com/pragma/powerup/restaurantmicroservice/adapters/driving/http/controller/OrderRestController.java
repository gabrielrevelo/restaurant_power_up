package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.SecurityCodeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.configuration.response.SuccessfulApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Order Controller", description = "Controller for managing orders")
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderHandler orderHandler;

    @Operation(summary = "[CLIENT] Create a new Order")
    @PostMapping()
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<SuccessfulApiResponse<Void>> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        orderHandler.createOrder(orderRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.ORDER_CREATED_MESSAGE));
    }

    @Operation(summary = "[EMPLOYEE] List orders by status")
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

    @Operation(summary = "[EMPLOYEE] Assign an order to an employee")
    @PatchMapping("/{idOrder}/assign")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> assignOrder(
            @PathVariable Long idOrder) {
        orderHandler.assignOrder(idOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.ORDER_ASSINGED_MESSAGE));
    }

    @Operation(summary = "[EMPLOYEE] Change Order to ready, to be delivered")
    @PatchMapping("/{idOrder}/ready")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> orderReady(
            @PathVariable Long idOrder) {
        orderHandler.orderReady(idOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.ORDER_READY_MESSAGE));
    }

    @Operation(summary = "[EMPLOYEE] Change Order to delivered")
    @PatchMapping("/{idOrder}/deliver")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> orderDelivered(
            @PathVariable Long idOrder, @RequestBody SecurityCodeRequestDto securityCodeRequestDto) {
        orderHandler.orderDelivered(idOrder, securityCodeRequestDto.getSecurityCode());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.ORDER_DELIVERED_MESSAGE));
    }

    @Operation(summary = "[CLIENT] Cancel an order")
    @PatchMapping("/{idOrder}/cancel")
    @SecurityRequirement(name = "jwt")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<SuccessfulApiResponse<List<OrderResponseDto>>> cancelOrder(
            @PathVariable Long idOrder) {
        orderHandler.cancelOrder(idOrder);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.ORDER_CANCELLED_MESSAGE));
    }
}
