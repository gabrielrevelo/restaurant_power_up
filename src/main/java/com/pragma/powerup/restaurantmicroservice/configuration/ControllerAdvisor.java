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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<ErrorApiResponse> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorApiResponse(WRONG_CREDENTIALS_MESSAGE));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorApiResponse> handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorApiResponse(WRONG_CREDENTIALS_MESSAGE));
    }

    @ExceptionHandler(UserNotOwnerException.class)
    public ResponseEntity<ErrorApiResponse> handleUserNotOwnerException(UserNotOwnerException userNotOwnerException) {
        String message = userNotOwnerException.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorApiResponse (message == null ? INVALID_OWNER_ID_MESSAGE : userNotOwnerException.getMessage()));
    }

    @ExceptionHandler(UserRoleNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleUserRoleNotFoundException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorApiResponse(USER_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleRestaurantNotFoundException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorApiResponse(RESTAURANT_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleDishNotFoundException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorApiResponse(DISH_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(httpMessageNotReadableException.getMessage()));
    }

    @ExceptionHandler(EmployeeCreationException.class)
    public ResponseEntity<ErrorApiResponse> handleEmployeeCreationException(EmployeeCreationException employeeCreationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(EMPLOYEE_NOT_CREATED_MESSAGE + ". (" + employeeCreationException.getMessage() + ")"));
    }

    @ExceptionHandler(ClientOrderInProgressException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderInProgressException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(ORDER_IN_PROGRESS_MESSAGE));
    }

    @ExceptionHandler(OrderNotEmployeeOfRestaurantException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotRestaurantEmployeeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(ORDER_NOT_RESTAURANT_EMPLOYEE_MESSAGE));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(ORDER_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(OrderNotReadyException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotReadyException(OrderNotReadyException orderNotReadyException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(ORDER_NOT_READY_MESSAGE + " (" + orderNotReadyException.getMessage() + ")"));
    }

    @ExceptionHandler(OrderNotPendingException.class)
    public ResponseEntity<ErrorApiResponse> handleOrderNotPendingException(OrderNotPendingException orderNotPendingException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorApiResponse(ORDER_NOT_PENDING_MESSAGE + " (" + orderNotPendingException.getMessage() + ")"));
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
