package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IUserClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserClient restTemplateClient;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, employeeRestaurantPersistencePort, restTemplateClient, userServicePort);
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
    void testListRestaurants() {
        // Arrange
        List<Restaurant> allRestaurants = Arrays.asList(
                new Restaurant("Restaurant A"),
                new Restaurant("Restaurant C"),
                new Restaurant("Restaurant B"),
                new Restaurant("Restaurant D")
        );

        List<Restaurant> expected = Arrays.asList(
                new Restaurant("Restaurant A"),
                new Restaurant("Restaurant B")
        );

        int pageSize = 2;
        int pageNumber = 1;

        when(restaurantPersistencePort.listRestaurants()).thenReturn(allRestaurants);

        // Act
        List<Restaurant> result = restaurantUseCase.listRestaurants(pageSize, pageNumber);

        // Assert
        assertEquals(expected, result);
        verify(restaurantPersistencePort, times(1)).listRestaurants();
    }
}
