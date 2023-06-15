package com.pragma.powerup.restaurantmicroservice.domain.api;

public interface ICurrentUserServicePort {
    String getCurrentUserId();

    String getCurrentUserToken();

    String getCurrentUserPhone();
}
