package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    @Test
    void testSaveDish() {
        Long id = 1L;
        String name = "Hamburguesa";
        Category category = new Category(1L, null, null);
        String description = "Deliciosa hamburguesa con queso";
        Double price = 15000.0;
        String idRestaurant = null;
        String urlImage = "https://ejemplo.com/hamburguesa.jpg";
        Boolean active = true;

        IDishPersistencePort dishPersistencePort = mock(IDishPersistencePort.class);
        DishUseCase dishUseCase = new DishUseCase(dishPersistencePort);

        Dish dish = new Dish(id, name, category, description, price, idRestaurant, urlImage, active);

        dishUseCase.saveDish(dish);

        ArgumentCaptor<Dish> dishCaptor = ArgumentCaptor.forClass(Dish.class);
        verify(dishPersistencePort, times(1)).saveDish(dishCaptor.capture());

        Dish capturedDish = dishCaptor.getValue();
        assertNotNull(capturedDish);
        assertEquals(id, capturedDish.getId());
        assertEquals(name, capturedDish.getName());
        assertEquals(category, capturedDish.getCategory());
        assertEquals(description, capturedDish.getDescription());
        assertEquals(price, capturedDish.getPrice());
        assertEquals(idRestaurant, capturedDish.getIdRestaurant());
        assertEquals(urlImage, capturedDish.getUrlImage());
        assertEquals(active, capturedDish.getActive());

        assertTrue(capturedDish.getActive());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        String name = "Hamburguesa";
        Category category = new Category(1L, null, null);
        String description = "Deliciosa hamburguesa con queso";
        Double price = 15000.0;
        String idRestaurant = null;
        String urlImage = "https://ejemplo.com/hamburguesa.jpg";
        Boolean active = true;

        IDishPersistencePort dishPersistencePort = mock(IDishPersistencePort.class);
        DishUseCase dishUseCase = new DishUseCase(dishPersistencePort);

        Dish expectedDish = new Dish(id, name, category, description, price, idRestaurant, urlImage, active);

        when(dishPersistencePort.findById(1L)).thenReturn(expectedDish);

        Dish actualDish = dishUseCase.findById(id);

        assertEquals(expectedDish, actualDish);

        verify(dishPersistencePort, times(1)).findById(1L);
    }

}