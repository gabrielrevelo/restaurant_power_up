package com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.dto.RoleResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.dto.UserResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.exceptions.EmployeeCreationException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;

public class UserClient implements IUserClient {
    private final UserApiClient userApiClient;

    public UserClient(UserApiClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    public Long getIdUserRole(String userId, String token) {
        try {
            CustomApiResponse<RoleResponseDto> responseData = userApiClient.getUserRole(userId, "Bearer " + token);
            RoleResponseDto roleResponseDto = responseData.getData();
            return roleResponseDto.getId();
        } catch (Exception ex) {
            throw new UserRoleNotFoundException();
        }
    }

    public Long createEmployee(Employee employee, String token) {
        try {
            CustomApiResponse<UserResponseDto> responseData = userApiClient.createEmployee(employee, "Bearer " + token);
            UserResponseDto userResponseDto = responseData.getData();
            return userResponseDto.getId();
        } catch (Exception ex) {
            throw new EmployeeCreationException(ex.getMessage());
        }
    }
}
