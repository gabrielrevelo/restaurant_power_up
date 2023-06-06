package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(Dish dish) {
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);
        dishRepository.save(dishEntity);
    }

    @Override
    public Dish findById(Long id) {
        DishEntity dishEntity = dishRepository.findById(id).orElseThrow(DishNotFoundException::new);
        return dishEntityMapper.toDomain(dishEntity);
    }

    @Override
    public List<Dish> listDishes(Long idRestaurant, Long categoryId, Pageable pageable) {
        if (categoryId == null) {
            return dishEntityMapper.toDomainList(
                    dishRepository.findAllByIdRestaurantOrderByNameAsc(idRestaurant, pageable).getContent());
        }
        return dishEntityMapper.toDomainList(
                dishRepository.findAllByIdRestaurantAndCategoryIdOrderByNameAsc(idRestaurant, categoryId, pageable).getContent());
    }
}