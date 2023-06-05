package com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.exceptions.EmployeeCreationException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;

public class UserClient implements IUserClient {
    private final UserMicroClient userMicroClient;

    @Autowired
    public UserClient(UserMicroClient userMicroClient) {
        this.userMicroClient = userMicroClient;
    }

    public Long getIdUserRole(String userId, String token) {
        try {
            CustomApiResponse<RoleResponseDto> responseData = userMicroClient.getUserRole(userId, "Bearer " + token);
            RoleResponseDto roleResponseDto = responseData.getData();
            return roleResponseDto.getId();
        } catch (Exception ex) {
            throw new UserRoleNotFoundException();
        }
    }

    public Long createEmployee(Employee employee, String token) {
        try {
            CustomApiResponse<UserResponseDto> responseData = userMicroClient.createEmployee(employee, "Bearer " + token);
            UserResponseDto userResponseDto = responseData.getData();
            return userResponseDto.getId();
        } catch (Exception ex) {
            throw new EmployeeCreationException(ex.getMessage());
        }
    }
}
