package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "userMicroClient", url = "${app.userMicroUrlBase}")
public interface UserMicroClient {
    @GetMapping("/role/{userId}")
    Map<String, String> getUserRole(@PathVariable("userId") String userId, @RequestHeader("Authorization") String authorization);
}