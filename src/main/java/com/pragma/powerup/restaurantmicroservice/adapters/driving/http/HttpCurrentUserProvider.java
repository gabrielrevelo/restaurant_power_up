package com.pragma.powerup.restaurantmicroservice.adapters.driving.http;

import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class HttpCurrentUserProvider implements ICurrentUserServicePort {


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public String getCurrentUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getCredentials();
    }

    @Override
    public String getCurrentUserPhone() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("phone");
    }
}
