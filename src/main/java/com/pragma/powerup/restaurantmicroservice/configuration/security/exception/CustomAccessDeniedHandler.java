package com.pragma.powerup.restaurantmicroservice.configuration.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.pragma.powerup.restaurantmicroservice.configuration.Constants.*;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put(RESPONSE_ERROR_MESSAGE_KEY, ACCESS_DENIED_MESSAGE);

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(jsonResponse));
        writer.flush();
    }
}
