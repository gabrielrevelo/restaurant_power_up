package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Page<DishEntity> findAllByIdRestaurantOrderByNameAsc(Long idRestaurant, Pageable pageable);
    Page<DishEntity> findAllByIdRestaurantAndCategoryIdOrderByNameAsc(Long idRestaurant, Long categoryId, Pageable pageable);
}
