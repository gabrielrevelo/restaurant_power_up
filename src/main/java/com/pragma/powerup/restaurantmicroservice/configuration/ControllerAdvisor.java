package com.pragma.powerup.restaurantmicroservice.configuration;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.exceptions.EmployeeCreationException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.userclient.exceptions.UserRoleNotFoundException;
import com.pragma.powerup.restaurantmicroservice.configuration.response.ErrorApiResponse;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.restaurantmicroservice.configuration.Constants.*;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, WRONG_CREDENTIALS_MESSAGE));
    }

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<Map<String, String>> handleUserNotOwnerException(UserNotOwnerException userNotOwnerException) {
        String message = userNotOwnerException.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, (message == null) ? INVALID_OWNER_ID_MESSAGE : userNotOwnerException.getMessage()));
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserRoleNotFoundException(UserRoleNotFoundException userRoleNotFoundException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, USER_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotFoundException(RestaurantNotFoundException restaurantNotFoundException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishNotFoundException(DishNotFoundException dishNotFoundException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, httpMessageNotReadableException.getMessage()));
    }

    @ExceptionHandler(EmployeeCreationException.class)
    public ResponseEntity<Object> handleEmployeeCreationException(EmployeeCreationException employeeCreationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(employeeCreationException.getMessage()));
    }

    @ExceptionHandler(ClientOrderInProgressException.class)
    public ResponseEntity<Object> handleOrderInProgressException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(Constants.ORDER_IN_PROGRESS_MESSAGE));
    }

    @ExceptionHandler(OrderNotEmployeeOfRestaurantException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotRestaurantEmployeeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(Constants.ORDER_NOT_RESTAURANT_EMPLOYEE_MESSAGE));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(Constants.ORDER_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(OrderNotReadyException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotReadyException(OrderNotReadyException orderNotReadyException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(Constants.ORDER_NOT_READY_MESSAGE + " (" + orderNotReadyException.getMessage() + ")"));
    }

    @ExceptionHandler(OrderNotPendingException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotPendingException(OrderNotPendingException orderNotPendingException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(Constants.ORDER_NOT_READY_MESSAGE + " (" + orderNotPendingException.getMessage() + ")"));
    }

    @ExceptionHandler(OrderNotBelongClientException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotBelongClientException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(ORDER_NOT_BELONG_CLIENT_MESSAGE));
    }

    @ExceptionHandler(InvalidSecurityCodeException.class)
    public ResponseEntity<ErrorApiResponse> handleInvalidSecurityCodeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(INVALID_SECURITY_CODE_ORDER_MESSAGE));
    }
}
