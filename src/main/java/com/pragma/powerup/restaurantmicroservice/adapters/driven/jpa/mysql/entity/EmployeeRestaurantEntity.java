package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "employee_restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRestaurantEntity {
    @EmbeddedId
    private EmployeeRestaurantId id;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class EmployeeRestaurantId implements Serializable {
        private Long idEmployee;
        private Long idRestaurant;
    }
}
