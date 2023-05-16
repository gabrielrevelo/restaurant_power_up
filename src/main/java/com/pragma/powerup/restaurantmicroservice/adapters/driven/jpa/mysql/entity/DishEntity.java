package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_category")
    private CategoryEntity category;
    private String description;
    private Double price;
    private String idRestaurant;
    private String urlImage;
    private Boolean active;
}
