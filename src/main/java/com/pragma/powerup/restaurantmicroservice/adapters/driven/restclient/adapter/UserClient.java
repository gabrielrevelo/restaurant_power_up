package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

public class UserClient implements IUserClient {
    private final UserMicroClient userMicroClient;

    @Autowired
    public UserClient(UserMicroClient userMicroClient) {
        this.userMicroClient = userMicroClient;
    }

    public String getUserRole(String userId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = (String) authentication.getCredentials();
            Map<String, String> responseData = userMicroClient.getUserRole(userId, "Bearer " + token);
            return responseData.get("role");
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserRoleNotFoundException();
        }
    }
}
