package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Boolean existsByIdClientAndStatusNot(Long idClient, String status);

    Page<OrderEntity> findAllByStatusAndIdRestaurant(String status, Long idRestaurant ,Pageable pageable);
}
