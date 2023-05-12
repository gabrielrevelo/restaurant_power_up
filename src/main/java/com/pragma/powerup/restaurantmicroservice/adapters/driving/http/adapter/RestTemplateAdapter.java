package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions.UserRoleNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateAdapter {
    @Value("${app.userMicroUrlBase}")
    private String userRoleUrl;
    RestTemplate restTemplate = new RestTemplate();
    public String getUserRole(String userId) {
        String url = userRoleUrl + "user/role/" + userId;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, String> responseData = response.getBody();
            if (responseData != null){
                return responseData.get("role");
            }else {
                throw new NullPointerException();
            }
        } else {
            throw new UserRoleNotFoundException();
        }
    }
}
