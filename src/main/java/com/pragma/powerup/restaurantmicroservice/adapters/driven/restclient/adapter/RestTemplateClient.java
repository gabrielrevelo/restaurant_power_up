package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestTemplateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestTemplateClient implements IRestTemplateClient {
    @Value("${app.userMicroUrlBase}")
    private String userMicroUrlBase;
    RestTemplate restTemplate = new RestTemplate();
    @Override
    public String getUserRole(String userId) {
        String url = userMicroUrlBase + "user/role/" + userId;
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
