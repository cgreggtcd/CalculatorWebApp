package com.softenggroup5.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CalculatorTests
{
    @Test
    void testCalculate() {
        assertEquals("0", Calculator.calculate(""));
    }

    @Test
    void testIsValid() {
        // Valid expressions
        assertTrue(Calculator.isValid("1+2+3+4"));
        assertTrue(Calculator.isValid("(1+2)*3-4^5/6"));
        assertTrue(Calculator.isValid("exp(1)"));
        assertTrue(Calculator.isValid("log(1.0)"));
        assertTrue(Calculator.isValid("9.0/874.5"));
        assertTrue(Calculator.isValid("-7"));
        assertTrue(Calculator.isValid("-7+-8"));
        assertTrue(Calculator.isValid("8^-0"));

        // Invalid repetition of operators/etc
        assertFalse(Calculator.isValid("-....."));
        assertFalse(Calculator.isValid("7++8"));
        assertFalse(Calculator.isValid("-7****+-7"));
        assertFalse(Calculator.isValid("-7//-7"));
        assertFalse(Calculator.isValid("-7--7"));
        assertFalse(Calculator.isValid("-7^^7"));
        assertFalse(Calculator.isValid("())"));

        // Invalid characters
        assertFalse(Calculator.isValid("((9.0/)874.5)"));
        assertFalse(Calculator.isValid("((9.0/874.5)"));
        assertFalse(Calculator.isValid("ex(0)"));
        assertFalse(Calculator.isValid("og(.)"));
        assertFalse(Calculator.isValid("exp0+8"));
        assertFalse(Calculator.isValid("This should fail."));
        assertFalse(Calculator.isValid("7-"));
        assertFalse(Calculator.isValid("^90"));
        assertFalse(Calculator.isValid("."));

        // Other tests
        assertFalse(Calculator.isValid("exp()"));
        assertFalse(Calculator.isValid("log()"));
    }
}