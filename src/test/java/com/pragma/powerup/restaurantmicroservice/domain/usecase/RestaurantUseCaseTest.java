package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IUserClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthorizationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserClient userClient;
    @Mock
    private IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    @Mock
    private AuthorizationUtil authorizationUtil;

    private RestaurantUseCase restaurantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort, employeeRestaurantPersistencePort, userClient, authorizationUtil);
    }

    @Test
    void saveRestaurant_ValidIdOwner() {
        String ownerId = "1";
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);
        when(userClient.getUserRole(ownerId, null)).thenReturn("ROLE_OWNER");

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(restaurant);
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurant_InvalidIdOwner() {
        String ownerId = "1";
        Restaurant restaurant = new Restaurant();
        restaurant.setIdOwner(ownerId);
        when(userClient.getUserRole(ownerId, null)).thenReturn("ROLE_ADMIN");

        assertThrows(UserNotOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        verify(restaurantPersistencePort, never()).saveRestaurant(restaurant);
    }

    @Test
    void listRestaurants() {
        List<Restaurant> allRestaurants = Arrays.asList(
                new Restaurant("Restaurant A"),
                new Restaurant("Restaurant C"),
                new Restaurant("Restaurant B"),
                new Restaurant("Restaurant D")
        );
        List<Restaurant> expected = Arrays.asList(
                new Restaurant("Restaurant C"),
                new Restaurant("Restaurant D")
        );
        int pageSize = 2;
        int pageNumber = 2;
        when(restaurantPersistencePort.listRestaurants()).thenReturn(allRestaurants);

        List<Restaurant> result = restaurantUseCase.listRestaurants(pageSize, pageNumber);

        assertEquals(expected, result);
        verify(restaurantPersistencePort).listRestaurants();
    }

    @Test
    void registerEmployee() {
        Employee employee = new Employee(
                1L,
                "Employee",
                "Example",
                "1",
                "1",
                "1",
                "1",
                LocalDate.now().minusYears(20),
                1L);
        Long restaurantId = 1L;
        when(userClient.createEmployee(employee, null)).thenReturn(employee.getId());

        Employee result = restaurantUseCase.registerEmployee(restaurantId, employee);

        assertEquals(employee, result);
        verify(employeeRestaurantPersistencePort).saveEmployeeRestaurant(employee.getId(), restaurantId);
    }
}
