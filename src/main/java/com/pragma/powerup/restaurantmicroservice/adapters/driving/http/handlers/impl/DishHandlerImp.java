package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishUpdateDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IDishMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishHandlerImp implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishMapper dishRequestMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(Long id, DishUpdateDto dishUpdateDto) {
        dishServicePort.updateDish(id, dishUpdateDto.getPrice(), dishUpdateDto.getDescription());
    }

    @Override
    public void changeStateDish(Long id) {
        dishServicePort.changeStateDish(id);
    }

    @Override
    public List<DishResponseDto> listDishes(Long idRestaurant, Long categoryId, Pageable pageable) {
        return dishRequestMapper.toResponseList(dishServicePort.listDishes(idRestaurant, categoryId, pageable));
    }
}
