package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderStatus;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;

    private final IOrderDishRepository orderDishRepository;

    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public void createOrder(Order order) {
        OrderEntity orderSaved = orderRepository.save(orderEntityMapper.toEntity(order));
        order.getMenuSelections().stream()
                .map(menuSelection -> new OrderDishEntity(
                        orderSaved.getId(),
                        menuSelection.getIdDish(),
                        menuSelection.getQuantity()))
                .forEach(orderDishRepository::save);
    }

    @Override
    public Boolean existsOrderInProcess(Long idClient) {
        return orderRepository.existsByIdClientAndStatusNot(idClient, OrderStatus.DELIVERED.name());
    }

    @Override
    public List<Order> listOrders(String status, Long idRestaurant, Pageable pageable) {
        List<OrderEntity> orderEntityList = orderRepository.findAllByStatusAndIdRestaurant(status, idRestaurant, pageable).getContent();
        List<Order> orderDomainList = orderEntityMapper.toDomainList(orderEntityList);
        orderDomainList.forEach(order -> {
            List<OrderDishEntity> orderDishEntityList = orderDishRepository.findByIdOrder(order.getId());
            order.setMenuSelections(orderEntityMapper.toDomainMenuSelectionsList(orderDishEntityList));
        });
        return orderDomainList;
    }
}