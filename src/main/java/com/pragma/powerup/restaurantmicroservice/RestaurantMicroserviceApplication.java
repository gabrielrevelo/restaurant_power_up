package com.pragma.powerup.restaurantmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients({"com.pragma.powerup.restaurantmicroservice.adapters.driven"})
public class RestaurantMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantMicroserviceApplication.class, args);
	}

}
