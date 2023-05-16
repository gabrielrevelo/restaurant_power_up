package com.pragma.powerup.restaurantmicroservice.configuration;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.restclient.adapter.RestTemplateClient;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestTemplateClient;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.restaurantmicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;

    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishRepository dishRepository;

    private final IDishEntityMapper dishEntityMapper;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }
    @Bean
    public IRestTemplateClient restTemplateClient() {
        return new RestTemplateClient();
    }
    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), restTemplateClient());
    }
    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, dishEntityMapper);
    }
    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort());
    }
}
