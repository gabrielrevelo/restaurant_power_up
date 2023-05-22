package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private ICurrentUserServicePort userServicePort;

    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantPersistencePort, userServicePort);
    }

    @Test
    void saveDish_WithValidOwner_AuthorizesAndSavesDish() {
        // Arrange
        Long restaurantId = 1L;
        String ownerId = "1";
        Dish dish = new Dish();
        dish.setIdRestaurant(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(userServicePort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act
        dishUseCase.saveDish(dish);

        // Assert
        assertTrue(dish.getActive());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void saveDish_WithInvalidOwner_ThrowsUserNotOwnerException() {
        // Arrange
        Long restaurantId = 1L;
        String ownerId = "1";
        String otherUserId = "2";
        Dish dish = new Dish();
        dish.setIdRestaurant(restaurantId);

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(userServicePort.getCurrentUserId()).thenReturn(otherUserId);
        when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        // Act & Assert
        assertThrows(UserNotOwnerException.class, () -> dishUseCase.saveDish(dish));
        verify(dishPersistencePort, never()).saveDish(dish);
    }

    @Test
    void updateDish_WithValidOwner_UpdatesDish() {
        // Arrange
        Long dishId = 1L;
        String ownerId = "1";
        Double newPrice = 9.99;
        String newDescription = "New Description";

        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setIdRestaurant(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        when(userServicePort.getCurrentUserId()).thenReturn(ownerId);
        when(restaurantPersistencePort.getRestaurant(dish.getIdRestaurant())).thenReturn(restaurant);

        // Act
        dishUseCase.updateDish(dishId, newPrice, newDescription);

        // Assert
        assertEquals(newPrice, dish.getPrice());
        assertEquals(newDescription, dish.getDescription());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void updateDish_WithInvalidOwner_ThrowsUserNotOwnerException() {
        // Arrange
        Long dishId = 1L;
        String ownerId = "1";
        String otherUserId = "2";
        Double newPrice = 9.99;
        String newDescription = "New Description";

        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setIdRestaurant(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);

        when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        when(userServicePort.getCurrentUserId()).thenReturn(otherUserId);
        when(restaurantPersistencePort.getRestaurant(dish.getIdRestaurant())).thenReturn(restaurant);

        // Act & Assert
        assertThrows(UserNotOwnerException.class, () -> dishUseCase.updateDish(dishId, newPrice, newDescription));
        verify(dishPersistencePort, never()).saveDish(dish);
    }

    @Test
    void changeStateDish_WithValidOwner_ShouldToggleDishState() {
        // Arrange
        Long dishId = 1L;
        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setIdRestaurant(1L);
        dish.setActive(true);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setIdOwner("1");

        when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        when(userServicePort.getCurrentUserId()).thenReturn("1");
        when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);

        // Act
        dishUseCase.changeStateDish(dishId);

        // Assert
        assertFalse(dish.getActive());
        verify(dishPersistencePort, times(1)).saveDish(dish);
    }
}
