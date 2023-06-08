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
public class OrderDishEntity {
    @EmbeddedId
    private OrderDishId id;
    private Integer quantity;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDishId implements Serializable {
        private Long idDish;
        private Long idOrder;
    }
}
