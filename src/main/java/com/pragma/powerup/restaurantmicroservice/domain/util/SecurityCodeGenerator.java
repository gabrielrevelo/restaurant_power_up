package com.pragma.powerup.restaurantmicroservice.domain.util;

import java.util.Random;

public class SecurityCodeGenerator {
    public static String generateCode() {
        Random random = new Random();
        String code = "";

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            code += digit;
        }

        return code;
    }
}
