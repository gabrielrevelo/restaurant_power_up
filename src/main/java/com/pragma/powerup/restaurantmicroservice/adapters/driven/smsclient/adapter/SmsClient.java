package com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.smsclient.dto.SmsRequestDto;
import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.spi.ISmsClient;

public class SmsClient implements ISmsClient {
    private final SmsApiClient smsApiClient;

    public SmsClient(SmsApiClient smsApiClient) {
        this.smsApiClient = smsApiClient;
    }

    public void sendSms(String phone, String securityCode, String token) {
        try {
            SmsRequestDto smsRequestDto = new SmsRequestDto(phone, securityCode);
            CustomApiResponse<Void> responseData = smsApiClient.sendSms(smsRequestDto, "Bearer " + token);
            System.out.println(responseData);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
