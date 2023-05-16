package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IDishMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImp implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishMapper dishRequestMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestMapper.toDish(dishRequestDto));
    }
}
