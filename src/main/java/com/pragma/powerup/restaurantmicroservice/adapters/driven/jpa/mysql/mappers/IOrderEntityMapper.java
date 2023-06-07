package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    OrderEntity toEntity(Order order);

    Order toDomain(OrderEntity orderEntity);

    List<Order> toDomainList(List<OrderEntity> orderEntities);

    @Mapping(target = "idDish", source = "idDish")
    @Mapping(target = "quantity", source = "quantity")
    Order.MenuSelection toDomainMenuSelection(OrderDishEntity orderDishEntity);

    List<Order.MenuSelection> toDomainMenuSelectionsList(List<OrderDishEntity> orderDishEntities);
}
