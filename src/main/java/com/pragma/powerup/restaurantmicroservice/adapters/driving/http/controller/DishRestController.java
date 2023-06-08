package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.controller;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.configuration.response.SuccessfulApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dishes/")
@RequiredArgsConstructor
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new Dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Dish not created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping()
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<SuccessfulApiResponse<Void>> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto) {
        dishHandler.saveDish(dishRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessfulApiResponse<>(Constants.DISH_CREATED_MESSAGE));
    }

    @Operation(summary = "Update a Dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Dish not updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<SuccessfulApiResponse<Void>> updateDish(
            @PathVariable("id") Long dishId,
            @Valid @RequestBody DishUpdateDto dishUpdateDto) {
        dishHandler.updateDish(dishId, dishUpdateDto);

        return ResponseEntity.ok()
                .body(new SuccessfulApiResponse<>(Constants.DISH_UPDATED_MESSAGE));
    }

    @PatchMapping("{id}/state")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<SuccessfulApiResponse<Void>> changeStateDish(
            @PathVariable("id") Long dishId) {
        dishHandler.changeStateDish(dishId);

        return ResponseEntity.ok()
                .body(new SuccessfulApiResponse<>(Constants.DISH_CHANGED_STATE_MESSAGE));
    }

    @GetMapping("/restaurant/{idRestaurant}")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<SuccessfulApiResponse<List<DishResponseDto>>> listDishes(
            @PathVariable Long idRestaurant,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        //Todo Move pageable to a handler
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber - 1);
        List<DishResponseDto> dishList = dishHandler.listDishes(idRestaurant, categoryId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessfulApiResponse<>(dishList));
    }
}
