package com.pragma.powerup.restaurantmicroservice.configuration;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.EmployeeRestaurantMysqlAdapter;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.OrderMysqlAdapter;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.*;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.adapter.SmsApiClient;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.adapter.SmsClient;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.adapter.UserApiClient;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.adapter.UserClient;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.HttpCurrentUserProvider;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.ICurrentUserServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.*;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.OrderUseCase;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.RestaurantUseCase;
import com.pragma.powerup.restaurantmicroservice.domain.util.AuthUtil;
import com.pragma.powerup.restaurantmicroservice.domain.util.CodeGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final UserApiClient userApiClient;
    private final SmsApiClient smsApiClient;
    @Bean
    public CodeGeneratorUtil codeGeneratorUtil() {
        return new CodeGeneratorUtil();
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public AuthUtil authUtil() {
        return new AuthUtil(restaurantPersistencePort(), employeeRestaurantPersistencePort(), userServicePort());
    }

    @Bean
    public IUserClient restTemplateClient() {
        return new UserClient(userApiClient);
    }

    @Bean
    public ISmsClient smsClient() {
        return new SmsClient(smsApiClient);
    }

    @Bean
    public IEmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantMysqlAdapter(employeeRestaurantRepository);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), employeeRestaurantPersistencePort(), restTemplateClient(), authUtil());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    public ICurrentUserServicePort userServicePort() {
        return new HttpCurrentUserProvider();
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), authUtil());
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), smsClient(), authUtil(), codeGeneratorUtil());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, orderDishRepository, orderEntityMapper);
    }
}
