package com.softenggroup5.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CalculatorTests
{
    @Test
    void testCalculate() {
        assertEquals("0", Calculator.calculate("0"));
    }

    @Test
    void testOrderOfOperations() {
        assertEquals("7.0", Calculator.calculate("1+2*3"));
        assertEquals("0.0", Calculator.calculate("1+2-3"));
        assertEquals("11.0", Calculator.calculate("3*4-1"));
        assertEquals("2.0", Calculator.calculate("1-2+3"));
        assertEquals("0.0", Calculator.calculate("-1-2+3"));
        assertEquals("-2.0", Calculator.calculate("-1+2-3"));
        assertEquals("-23.0", Calculator.calculate("8+6-7*4+3-2*6"));
        assertEquals("-76396.0", Calculator.calculate("12435+34569-12345*10+50"));
        assertEquals("-2335046.0", Calculator.calculate("697*1884-7034*519+2452"));
        assertEquals("-5525864.0", Calculator.calculate("-20-662-1417*3899-299"));
        assertEquals("3497.0", Calculator.calculate("-4002+763*7+2158"));
        assertEquals("8.0", Calculator.calculate("1.0+2*3.5"));
        assertEquals("0.78", Calculator.calculate("1+2.78-3"));
        assertEquals("-0.031", Calculator.calculate("3.876/4-1")); 
        assertEquals("2.0", Calculator.calculate("1-2+3"));
        assertEquals("0.1", Calculator.calculate("-0.9-2+3"));
        assertEquals("-35.743", Calculator.calculate("8.8/7-7*4+3.0/-2*6"));
    }

    @Test
    void testIsValid() {
        // Valid expressions
        assertTrue(Calculator.isValid("1+2+3+4"));
        //assertTrue(Calculator.isValid("(1+2)*3-4^5/6"));
        //assertTrue(Calculator.isValid("exp(1)"));
        //assertTrue(Calculator.isValid("log(1.0)"));
        assertTrue(Calculator.isValid("9.0/874.5"));
        assertTrue(Calculator.isValid("-7"));
        assertTrue(Calculator.isValid("-7+-8"));
        //assertTrue(Calculator.isValid("8^-0"));

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
        assertFalse(Calculator.isValid("*./"));

        // Other tests
        assertFalse(Calculator.isValid("exp()"));
        assertFalse(Calculator.isValid("log()"));

        // Test top level function
        assertEquals("That input is not valid. Please try again.", Calculator.calculate("This should fail."));
    }
}