package com.pragma.powerup.restaurantmicroservice.adapters.driven.tarceadapter.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.tarceadapter.dto.TraceabilityRequestDto;
import com.pragma.powerup.restaurantmicroservice.configuration.response.CustomApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.spi.ITraceClient;

public class TraceClient implements ITraceClient {
    private final TraceApiClient traceApiClient;

    public TraceClient(TraceApiClient traceApiClient) {
        this.traceApiClient = traceApiClient;
    }

    @Override
    public void trace(Order order, String oldStatus, String token) {
        try {
            TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
            traceabilityRequestDto.setIdOrder(String.valueOf(order.getId()));
            traceabilityRequestDto.setIdClient(String.valueOf(order.getIdClient()));
            traceabilityRequestDto.setIdEmployee(String.valueOf(order.getIdChef()));
            traceabilityRequestDto.setNewStatus(order.getStatus().name());
            traceabilityRequestDto.setOldStatus(oldStatus);
            CustomApiResponse<Void> responseData = traceApiClient.trace(traceabilityRequestDto, "Bearer " + token);
            System.out.println(responseData);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
