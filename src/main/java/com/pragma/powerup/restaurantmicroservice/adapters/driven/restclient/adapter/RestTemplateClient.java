package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestTemplateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestTemplateClient implements IRestTemplateClient {
    @Value("${app.userMicroUrlBase}")
    private String userMicroUrlBase;
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getUserRole(String userId) {
        try {
            String url = userMicroUrlBase + "user/role/" + userId;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new UserRoleNotFoundException();
            }
            Map<String, String> responseData = response.getBody();
            return responseData.get("role");
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserRoleNotFoundException();
        }
    }
}
