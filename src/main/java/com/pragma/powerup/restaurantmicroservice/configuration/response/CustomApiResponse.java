package com.pragma.powerup.restaurantmicroservice.configuration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomApiResponse<T> {
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorDetails error;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ErrorDetails {
        private String code;
        private String message;
    }
}
