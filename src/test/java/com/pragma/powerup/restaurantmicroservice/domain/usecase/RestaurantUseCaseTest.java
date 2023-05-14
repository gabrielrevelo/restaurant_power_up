package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestTemplateClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IRestTemplateClient restTemplateClient;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveRestaurant_ValidOwner_SaveSuccessful() {
        // Arrange
        String ownerId = "123";
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(restTemplateClient.getUserRole(ownerId)).thenReturn("ROLE_OWNER");

        // Act
        restaurantUseCase.saveRestaurant(restaurant);

        // Assert
        verify(restTemplateClient, times(1)).getUserRole(ownerId);
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void testSaveRestaurant_InvalidOwner_ThrowsUserNotOwnerException() {
        // Arrange
        String ownerId = "123";
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(restTemplateClient.getUserRole(ownerId)).thenReturn("ROLE_ADMIN");

        // Act & Assert
        assertThrows(UserNotOwnerException.class, () -> {
            restaurantUseCase.saveRestaurant(restaurant);
        });
    }
}
