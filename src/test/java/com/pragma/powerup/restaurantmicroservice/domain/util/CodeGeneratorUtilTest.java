package com.pragma.powerup.restaurantmicroservice.domain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CodeGeneratorUtilTest {
    @Test
    void testGenerateCode() {
        CodeGeneratorUtil codeGeneratorUtil = new CodeGeneratorUtil();

        String code = codeGeneratorUtil.generateCode();

        Assertions.assertEquals(4, code.length());
        Assertions.assertTrue(code.matches("\\d+"));
    }
}