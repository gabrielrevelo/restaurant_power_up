package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public Restaurant getRestaurant(Long idRestaurant) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(idRestaurant)
                .orElseThrow(RestaurantNotFoundException::new);
        return restaurantEntityMapper.toDomain(restaurantEntity);
    }

    @Override
    public List<Restaurant> listRestaurants() {
        List<RestaurantEntity> roleEntityList = restaurantRepository.findAll();
        if (roleEntityList.isEmpty()) {
            throw new RestaurantNotFoundException();
        }
        return restaurantEntityMapper.toDomainList(roleEntityList);
    }
}