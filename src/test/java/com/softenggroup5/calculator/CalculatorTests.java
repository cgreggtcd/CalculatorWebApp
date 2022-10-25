package com.softenggroup5.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    @Test
    public void convertToPostfixTest() {
        String postfix = Calculator.convertToPostfix("2.54+3.34+4.3");
        String correctPostfix = "2.54 3.34 + 4.3 + ";
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("2.432*3.3-4.4");
        correctPostfix = "2.432 3.3 * 4.4 - ";
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("2*3-4-0-3-5+2+10*20-4*100*10000");
        correctPostfix = "2 3 * 4 - 0 - 3 - 5 - 2 + 10 20 * + 4 100 * 10000 * - ";
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("5*3+2-5+2");
        correctPostfix = "5 3 * 2 + 5 - 2 + ";
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("5.5*43/5+3.4");
        correctPostfix = "5.5 43 * 5 / 3.4 + ";
        assertEquals(correctPostfix, postfix);
    }
    @Test
    public void evalPostfixTest() {
        String result = Calculator.evaluatePostfix("2 3 + 4 +");
        String correct = "9.0";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("2 3 * 4 - 0 - 3 - 5 - 2 + 10 20 * + 4 100 * 10000 * - ");
        correct = "-3999804.0";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("5 3 * 2 + 5 - 2 + ");
        correct = "14.0";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("5 7 2 * + 6 - 2 - ");
        correct = "11.0";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("5.5 43 * 5 / 3.4 + ");
        correct = "50.7";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("2.432 3.3 * 4.4 - ");
        correct = "3.626";
        assertEquals(correct, result);
    }
    @ParameterizedTest
    @ValueSource(chars = {'+', '-', '*', '/'})
    public void isOperatorTest(char input) {
        boolean result = Calculator.isOperator(input);
        assertEquals(true, result);

        result = Calculator.isOperator('h');
        assertEquals(false, result);

        result = Calculator.isOperator('3');
        assertEquals(false, result);
    }
}



