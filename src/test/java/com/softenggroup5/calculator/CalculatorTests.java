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
        assertEquals("0.0", Calculator.calculate("0+0")); //test zero case
        assertEquals("100.1", Calculator.calculate("99+0+1.1")); //test zero addition case
        assertEquals("10.0", Calculator.calculate("1+2+3+4")); //test normal integer case
        assertEquals("25098.0", Calculator.calculate("12345+12446+1+306")); //test big integer case
        assertEquals("4.66", Calculator.calculate("1.22+1.33+2.11")); //test normal floating number case
        assertEquals("7136.16", Calculator.calculate("234.66+6789.7+34+77.8")); //test big floating number case
        assertEquals("0.0", Calculator.calculate("0*0")); //test multiplication zero case
        assertEquals("0.0", Calculator.calculate("0*768*899.79*99.9765")); //test zero case multiply
        assertEquals("576.0", Calculator.calculate("12*2*4*6")); //test normal multiplication
        assertEquals("3343.044", Calculator.calculate("1.22*456.7*6")); //test big floating number case
        assertEquals("2385.0", Calculator.calculate("8+7*2+56*40+123")); //test normal addition and multiplication case
        assertEquals("144915.0", Calculator.calculate("789+456+4789*30+765*0")); //test addition and multiplication case
        assertEquals("923.52", Calculator.calculate("1.3+4.22+765*1.2")); //test complex addition and multiplication case
        assertEquals("4899.22", Calculator.calculate("1.333*0+765*6.4+3.22")); // test complex addition and multiplication case
        assertEquals("1295.8", Calculator.calculate("12345-11456+99*8.6-456+8.2+6.4/2")); //test complex calculation
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

    // Please uncomment relevant tests when you implement functionality for them (brackets, ^, log and exp)
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



