package com.pragma.powerup.restaurantmicroservice.adapters.driven.tarceadapter.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.tarceadapter.dto.TraceabilityRequestDto;
import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "traceApiClient", url = "${app.traceMicroUrlBase}")
public interface TraceApiClient {
    @PostMapping("/trace")
    CustomApiResponse<Void> trace(TraceabilityRequestDto traceabilityRequestDto, @RequestHeader("Authorization") String token);
}