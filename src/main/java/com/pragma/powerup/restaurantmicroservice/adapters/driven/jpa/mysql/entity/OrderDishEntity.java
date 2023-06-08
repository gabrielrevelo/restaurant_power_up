package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "order_dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@IdClass(OrderDishEntity.OrderDishId.class)
public class OrderDishEntity {
    @Id
    private Long idDish;
    @Id
    private Long idOrder;
    private Integer quantity;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDishId implements Serializable {
        private Long idDish;
        private Long idOrder;
    }
}
