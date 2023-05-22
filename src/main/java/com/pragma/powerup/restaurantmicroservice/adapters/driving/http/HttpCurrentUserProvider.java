package com.pragma.powerup.restaurantmicroservice.adapters.driving.http;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HttpCurrentUserProvider implements ICurrentUserServicePort {

    @Autowired
    private HttpServletRequest request;

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
