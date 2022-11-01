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
        assertEquals("1.0", Calculator.calculate("0^0")); //test zero case
        assertEquals("1.0", Calculator.calculate("12^0"));
        assertEquals("1.0", Calculator.calculate("1.232^0"));
        assertEquals("6.25", Calculator.calculate("2^-2+6"));
        assertEquals("30.248", Calculator.calculate("2^-2+6-8^-3+9*3-6/2"));
        assertEquals("35.0", Calculator.calculate("3^3+9-2^0"));
        assertEquals("15.0", Calculator.calculate("2^3+9-2"));
        assertEquals("19.448", Calculator.calculate("2.2^3+9-2.2+4/2"));
        assertEquals("-2.512", Calculator.calculate("1.22^2+12-6*3+4/2")); //test complex calculation
        assertEquals("-3.503", Calculator.calculate("2^1.2+12-6*3+4/2-3^2-1*6+12.2+3.6789^0")); //test complex calculation
        assertEquals("29.487", Calculator.calculate("2^1.2+3-2+5^-2+8*3-6/3+6^-0+3.15")); //test complex calculation
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
        
        postfix = Calculator.convertToPostfix("3^(1+2)");
        correctPostfix = "3 1 2 + ^ ";
        assertEquals(correctPostfix, postfix);
        
        postfix = Calculator.convertToPostfix("3.1^(1+2)+2.3");
        correctPostfix = "3.1 1 2 + ^ 2.3 + ";
        assertEquals(correctPostfix, postfix);
        
        postfix = Calculator.convertToPostfix("3.1^(1+2)+2.3-5*2");
        correctPostfix = "3.1 1 2 + ^ 2.3 + 5 2 * - ";
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("(2.54+3.34)+4.3");
        correctPostfix = "2.54 3.34 + 4.3 + ";
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("5*(3+2)-5+2");
        correctPostfix = "5 3 2 + * 5 - 2 + "; // 22
        assertEquals(correctPostfix, postfix);

        postfix = Calculator.convertToPostfix("(5*3+2*(-5+2))");
        correctPostfix = "5 3 * 2 -5 2 + * + "; // 9
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
        
        postfix = Calculator.convertToPostfix("5.6*(1+6)-2^(6+1)+1.2*6/3");
        correctPostfix = "5.6 1 6 + * 2 6 1 + ^ - 1.2 6 * 3 / + ";
        assertEquals(correctPostfix, postfix);
        
        postfix = Calculator.convertToPostfix("5.9*48/2+3.6+6^-2-2^1.2");
        correctPostfix = "5.9 48 * 2 / 3.6 + 6 -2 ^ + 2 1.2 ^ - ";
        assertEquals(correctPostfix, postfix);
        
        postfix = Calculator.convertToPostfix("2^(1+6)+36*5.2-6/2+2.1^2+8^-2+3.124^0+20*(1+6)-(7+1)/2+20*8.6+(9.88-2.65)*6");
        correctPostfix = "2 1 6 + ^ 36 5.2 * + 6 2 / - 2.1 2 ^ + 8 -2 ^ + 3.124 0 ^ + 20 1 6 + * + 7 1 + 2 / - 20 8.6 * + 9.88 2.65 - 6 * + ";
        assertEquals(correctPostfix, postfix);
        
        postfix = Calculator.convertToPostfix("5.2*26/6+3.6+6^-2-2^1.2+9/3.6-2*200+3.67+2^0-1000+20*200*60000");
        correctPostfix = "5.2 26 * 6 / 3.6 + 6 -2 ^ + 2 1.2 ^ - 9 3.6 / + 2 200 * - 3.67 + 2 0 ^ + 1000 - 20 200 * 60000 * + ";
        assertEquals(correctPostfix, postfix);
        
    }
    @Test
    public void evalPostfixTest() {
        String result = Calculator.evaluatePostfix("2 3 + 4 +");
        String correct = "9.0";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("5 3 2 + * 5 - 2 + ");
        correct = "22.0";
        assertEquals(correct, result);

        result = Calculator.evaluatePostfix("5 3 * 2 -5 2 + * + ");
        correct = "9.0";
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
        
        result = Calculator.evaluatePostfix("2.66 6.9 * 5.9 - 2 3 ^ + ");
        correct = "20.454";
        assertEquals(correct, result);
        
        result = Calculator.evaluatePostfix("2.66 6 * 5.9 - 6 3 ^ + 20 10 * 1000 * + 2 1.2 ^ + 3.2 2 ^ - 6 2 / + 8 -2 ^ + 6 -3 ^ + ");
        correct = "200221.14";
        assertEquals(correct, result);
        
    }

    @ParameterizedTest
    @ValueSource(chars = {'+', '-', '*', '/', '^'})
    public void isOperatorTest(char input) {
        boolean result = Calculator.isOperator(input);
        assertEquals(true, result);

        result = Calculator.isOperator('h');
        assertEquals(false, result);

        result = Calculator.isOperator('3');
        assertEquals(false, result);
    }
}

