package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;
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

    @Test
    void listDishes() {
        List<Dish> allRestaurants = Arrays.asList(
                new Dish(1L, "Dish A", new Category(1L, null, null), 1L, true),
                new Dish(2L, "Dish C", new Category(3L, null, null), 2L, true),
                new Dish(3L, "Dish B", new Category(2L, null, null), 2L, true),
                new Dish(4L, "Dish D", new Category(1L, null, null), 1L, true),
                new Dish(5L, "Dish E", new Category(2L, null, null), 1L, true),
                new Dish(6L, "Dish F", new Category(1L, null, null), 1L, true)
        );
        List<Dish> expected = Arrays.asList(
                new Dish(1L, "Dish A", new Category(1L, null, null), 1L, true),
                new Dish(4L, "Dish D", new Category(1L, null, null), 1L, true)
        );
        Pageable pageable = Pageable.ofSize(2).withPage(0);
        when(dishPersistencePort.listDishes(1L, 1L, pageable)).thenReturn(expected);

        List<Dish> actual = dishUseCase.listDishes(1L, 1L, pageable);

        assertEquals(expected, actual);
        verify(dishPersistencePort).listDishes(1L, 1L, pageable);
    }
}
