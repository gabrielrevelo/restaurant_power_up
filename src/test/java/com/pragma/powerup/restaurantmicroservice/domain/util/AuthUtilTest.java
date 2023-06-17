package com.pragma.powerup.restaurantmicroservice.domain.util;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.ClientOrderInProgressException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotBelongClientException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderNotEmployeeOfRestaurantException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.UserNotOwnerException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeeRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.ISmsClient;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.OrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthUtilTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    @Mock
    private ICurrentUserServicePort currentUserServicePort;

    private AuthUtil authUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authUtil = new AuthUtil(restaurantPersistencePort, employeeRestaurantPersistencePort, currentUserServicePort);
    }

    @Test
    void checkOwnerOfRestaurant() {
        Long idRestaurant = 1L;
        Long idCurrentUser = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        restaurant.setIdOwner(String.valueOf(idCurrentUser));
        when(currentUserServicePort.getCurrentUserId()).thenReturn(String.valueOf(idCurrentUser));
        when(restaurantPersistencePort.getRestaurant(idRestaurant)).thenReturn(restaurant);

        authUtil.checkOwnerOfRestaurant(idRestaurant);

        verify(currentUserServicePort).getCurrentUserId();
        verify(restaurantPersistencePort).getRestaurant(idRestaurant);
        verifyNoMoreInteractions(currentUserServicePort, restaurantPersistencePort);
    }

    @Test
    void checkOwnerOfRestaurant_UserNotOwner() {
        Long idRestaurant = 1L;
        Long idOwner = 1L;
        Long idCurrentUser = 2L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idRestaurant);
        restaurant.setIdOwner(String.valueOf(idOwner));
        when(currentUserServicePort.getCurrentUserId()).thenReturn(String.valueOf(idCurrentUser));
        when(restaurantPersistencePort.getRestaurant(idRestaurant)).thenReturn(restaurant);

        assertThrows(UserNotOwnerException.class, () -> authUtil.checkOwnerOfRestaurant(idRestaurant));
    }

    @Test
    void checkEmployeeOfRestaurant_() {
        Long idRestaurant = 1L;
        Long idRestaurantOfEmployee = 1L;

        assertDoesNotThrow(() -> authUtil.checkEmployeeOfRestaurant(idRestaurant, idRestaurantOfEmployee));
    }

    @Test
    void checkEmployeeOfRestaurant() {
        Long idRestaurant = 1L;
        Long idRestaurantOfEmployee = 2L;

        assertThrows(OrderNotEmployeeOfRestaurantException.class, () -> authUtil.checkEmployeeOfRestaurant(idRestaurant, idRestaurantOfEmployee));
    }

    @Test
    void checkClientOfOrder() {
        Long idClient = 1L;
        Long idCurrentUser = 1L;
        Order order = new Order();
        order.setIdClient(idClient);
        when(currentUserServicePort.getCurrentUserId()).thenReturn(String.valueOf(idCurrentUser));

        authUtil.checkClientOfOrder(order);

        verify(currentUserServicePort).getCurrentUserId();
        verifyNoMoreInteractions(currentUserServicePort, employeeRestaurantPersistencePort);
    }

    @Test
    void checkClientOfOrder_OrderNotBelongClient() {
        Long idClient = 1L;
        Long idCurrentUser = 2L;
        Order order = new Order();
        order.setIdClient(idClient);
        when(currentUserServicePort.getCurrentUserId()).thenReturn(String.valueOf(idCurrentUser));

        assertThrows(OrderNotBelongClientException.class, () -> authUtil.checkClientOfOrder(order));
    }

    @Test
    void getCurrentUserToken() {
        String token = "token";
        when(currentUserServicePort.getCurrentUserToken()).thenReturn(token);

        String result = authUtil.getCurrentUserToken();

        assertEquals(token, result);
    }

    @Test
    void getCurrentUserId() {
        String idCurrentUser = "1";
        when(currentUserServicePort.getCurrentUserId()).thenReturn(idCurrentUser);

        Long result = authUtil.getCurrentUserId();

        assertEquals(Long.valueOf(idCurrentUser), result);
    }

    @Test
    void getCurrentEmployeeRestaurantId() {
        Long idEmployee = 1L;
        Long idRestaurant = 1L;
        when(currentUserServicePort.getCurrentUserId()).thenReturn(idEmployee.toString());
        when(employeeRestaurantPersistencePort.findRestaurantIdByEmployeeId(idEmployee)).thenReturn(idRestaurant);

        Long result = authUtil.getCurrentEmployeeRestaurantId();

        assertEquals(idRestaurant, result);
    }

    @Test
    void getCurrentUserPhone() {
        String phone = "+573000000000";
        when(currentUserServicePort.getCurrentUserPhone()).thenReturn(phone);

        String result = authUtil.getCurrentUserPhone();

        assertEquals(phone, result);
    }
}