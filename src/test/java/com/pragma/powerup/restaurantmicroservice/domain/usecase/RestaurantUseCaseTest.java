package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestTemplateClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IRestTemplateClient restTemplateClient;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, restTemplateClient);
    }

    @Test
    void saveRestaurant_WithValidOwner_SavesRestaurant() {
        // Arrange
        String ownerId = "1";
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(restTemplateClient.getUserRole(ownerId)).thenReturn("ROLE_OWNER");

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(restaurantPersistencePort).saveRestaurant(restaurant);
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_WithInvalidOwner_ThrowsUserNotOwnerException() {
        // Arrange
        String ownerId = "1";
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(restTemplateClient.getUserRole(ownerId)).thenReturn("ROLE_ADMIN");

        // Act & Assert
        assertThrows(UserNotOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        verify(restaurantPersistencePort, never()).saveRestaurant(restaurant);
    }

    @Test
    void listRestaurants_ReturnsListOfRestaurants_WhenRestaurantsExist() {
        // Arrange
        List<Restaurant> expectedRestaurants = Collections.singletonList(new Restaurant());
        when(restaurantPersistencePort.listRestaurants()).thenReturn(expectedRestaurants);

        // Act
        List<Restaurant> result = restaurantUseCase.listRestaurants();

        // Assert
        assertEquals(expectedRestaurants, result);
        verify(restaurantPersistencePort, times(1)).listRestaurants();
    }
}
