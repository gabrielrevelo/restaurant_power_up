package com.pragma.powerup.restaurantmicroservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RestaurantTest {

    @Test
    void testRestaurant_GettersAndSetters() {
        // Arrange
        Long id = 1L;
        String name = "Restaurant Name";
        String address = "Restaurant Address";
        String idOwner = "Owner ID";
        String phone = "Restaurant Phone";
        String urlLogo = "Logo URL";
        String nit = "Restaurant NIT";

        // Act
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setIdOwner(idOwner);
        restaurant.setPhone(phone);
        restaurant.setUrlLogo(urlLogo);
        restaurant.setNit(nit);

        // Assert
        assertEquals(id, restaurant.getId());
        assertEquals(name, restaurant.getName());
        assertEquals(address, restaurant.getAddress());
        assertEquals(idOwner, restaurant.getIdOwner());
        assertEquals(phone, restaurant.getPhone());
        assertEquals(urlLogo, restaurant.getUrlLogo());
        assertEquals(nit, restaurant.getNit());
    }
}
