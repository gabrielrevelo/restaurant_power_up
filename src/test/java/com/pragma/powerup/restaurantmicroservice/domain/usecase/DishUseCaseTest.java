package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
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
    @Mock
    private AuthorizationUtil authorizationUtil;

    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort, authorizationUtil);
    }

    @Test
    void saveDish() {
        Dish dish = new Dish();

        dishUseCase.saveDish(dish);

        assertTrue(dish.getActive());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void updateDish() {
        Dish dish = new Dish();
        dish.setId(1L);
        Double newPrice = 10.0;
        String newDescription = "New Description";
        when(dishPersistencePort.findById(dish.getId())).thenReturn(dish);

        dishUseCase.updateDish(dish.getId(), newPrice, newDescription);

        assertEquals(newPrice, dish.getPrice());
        assertEquals(newDescription, dish.getDescription());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void changeStateDish_trueToFalse() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setActive(true);
        when(dishPersistencePort.findById(dish.getId())).thenReturn(dish);

        dishUseCase.changeStateDish(dish.getId());

        assertFalse(dish.getActive());
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    void changeStateDish_falseToTrue() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setActive(false);
        when(dishPersistencePort.findById(dish.getId())).thenReturn(dish);

        dishUseCase.changeStateDish(dish.getId());

        assertTrue(dish.getActive());
        verify(dishPersistencePort).saveDish(dish);
    }
}
