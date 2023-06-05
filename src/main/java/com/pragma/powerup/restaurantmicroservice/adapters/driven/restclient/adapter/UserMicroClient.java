package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userMicroClient", url = "${app.userMicroUrlBase}")
public interface UserMicroClient {
    @GetMapping("/role/{userId}")
    CustomApiResponse<RoleResponseDto> getUserRole(@PathVariable("userId") String userId, @RequestHeader("Authorization") String token);

    @PostMapping("/user/employee")
    CustomApiResponse<UserResponseDto> createEmployee(Employee employee, @RequestHeader("Authorization") String token);
}