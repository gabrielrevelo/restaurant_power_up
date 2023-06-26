package com.pragma.powerup.restaurantmicroservice.configuration;

public class Constants {
    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long OWNER_ROLE_ID = 2L;
    public static final Long EMPLOYEE_ROLE_ID = 3L;
    public static final Long CLIENT_ROLE_ID = 4L;
    public static final int MAX_PAGE_SIZE = 2;
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String ORDER_CREATED_MESSAGE = "Order created successfully";
    public static final String USER_CREATED_MESSAGE = "User created successfully";
    public static final String USER_DELETED_MESSAGE = "User deleted successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials or role not allowed";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String PERSON_ALREADY_EXISTS_MESSAGE = "A person already exists with the DNI number provided";
    public static final String MAIL_ALREADY_EXISTS_MESSAGE = "A person with that mail already exists";
    public static final String PERSON_NOT_FOUND_MESSAGE = "No person found with the id provided";
    public static final String ROLE_NOT_FOUND_MESSAGE = "No role found with the id provided";
    public static final String ROLE_NOT_ALLOWED_MESSAGE = "No permission granted to create users with this role";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "A user already exists with the role provided";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found";
    public static final String ORDER_NOT_FOUND_MESSAGE = "No order found";
    public static final String ORDER_NOT_READY_MESSAGE = "Order not ready";
    public static final String ORDER_NOT_PENDING_MESSAGE = "Order not pending";
    public static final String ORDER_NOT_BELONG_CLIENT_MESSAGE = "Order not belong to client";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "No restaurant found";
    public static final String DISH_NOT_FOUND_MESSAGE = "No dish found";
    public static final String SWAGGER_TITLE_MESSAGE = "Restaurant API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
    public static final String EMPTY_FIELD_MESSAGE = "The field cannot be empty";
    public static final String INVALID_FORMAT_MESSAGE = "Invalid format";
    public static final String INVALID_OWNER_ID_MESSAGE = "Invalid ID for owner user";
    public static final String DISH_UPDATED_MESSAGE = "Dish updated successfully";
    public static final String ACCESS_DENIED_MESSAGE = "Access denied. Insufficient permissions.";
    public static final String DISH_CHANGED_STATE_MESSAGE = "Dish state changed successfully";
    public static final String ORDER_IN_PROGRESS_MESSAGE = "Order in progress for this user";
    public static final String ORDER_NOT_RESTAURANT_EMPLOYEE_MESSAGE = "Order not belong to restaurant employee";
    public static final String INVALID_SECURITY_CODE_ORDER_MESSAGE = "Invalid security code for order";
    public static final String ORDER_ASSINGED_MESSAGE = "Order assigned successfully";
    public static final String ORDER_READY_MESSAGE = "Order ready successfully";
    public static final String ORDER_DELIVERED_MESSAGE = "Order delivered successfully";
    public static final String ORDER_CANCELLED_MESSAGE = "Order cancelled successfully";
    public static final String EMPLOYEE_NOT_CREATED_MESSAGE = "Employee not created";

}
