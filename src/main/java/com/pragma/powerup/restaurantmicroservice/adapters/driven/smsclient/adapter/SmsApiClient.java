package com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.dto.SmsRequestDto;
import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "smsApiClient", url = "${app.smsMicroUrlBase}")
public interface SmsApiClient {
    @PostMapping("/sms")
    CustomApiResponse<Void> sendSms(SmsRequestDto smsRequestDto, @RequestHeader("Authorization") String token);
}