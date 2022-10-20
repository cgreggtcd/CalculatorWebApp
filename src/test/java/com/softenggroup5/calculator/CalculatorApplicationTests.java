package com.softenggroup5.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import App.java

@SpringBootTest
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
 
public class AppTest 
{
    @Test
    void contextLoads() {
    }
    
    @Test
    public void isValidTestTrue() {
        boolean valid = App.isValid("1+2+3");
        assertEquals(true, valid);
    }
    @Test
    public void isValidTestFalse() {
        boolean valid = App.isValid("hi there");
        assertEquals(false, valid);
    }
    @Test
    public void isValidTestFalseTwo() {
        boolean valid = App.isValid("1+5*3@4");
        assertEquals(false, valid);
    }
    @Test
    public void convertToPostfixTest() {
        String postfix = App.convertToPostfix("2+3+4");
        String correctPostfix = "2 3 + 4 + ";
        assertEquals(correctPostfix, postfix);
    }
    @Test
    public void convertToPostfixTestTwo() {
        String postfix = App.convertToPostfix("2*3-4");
        String correctPostfix = "2 3 * 4 - ";
        assertEquals(correctPostfix, postfix);
    }
    @Test
    public void convertToPostfixTestThree() {
        String postfix = App.convertToPostfix("2*3-4-0-3-5+2+10*20-4*100*10000");
        String correctPostfix = "2 3 * 4 - 0 - 3 - 5 - 2 + 10 20 * + 4 100 * 10000 * - ";
        assertEquals(correctPostfix, postfix);
    }
    @Test
    public void convertToPostfixTestFour() {
        String postfix = App.convertToPostfix("5*3+2-5+2");
        String correctPostfix = "5 3 * 2 + 5 - 2 + ";
        assertEquals(correctPostfix, postfix);
    }
    @Test
    public void evalPostfixTest() {
        String result = App.evaluatePostfix("2 3 + 4 +");
        String correct = "9";
        assertEquals(correct, result);
    }
    @Test
    public void evalPostfixTestTwo() {
        String result = App.evaluatePostfix("2 3 * 4 - 0 - 3 - 5 - 2 + 10 20 * + 4 100 * 10000 * - ");
        String correct = "-3999804";
        assertEquals(correct, result);
    }
    @Test
    public void evalPostfixTestThree() {
        String result = App.evaluatePostfix("5 3 * 2 + 5 - 2 + ");
        String correct = "14";
        assertEquals(correct, result);
    }
    @Test
    public void evalPostfixTestFour() {
        String result = App.evaluatePostfix("5 7 2 * + 6 - 2 - ");
        String correct = "11";
        assertEquals(correct, result);
    }
    @ParameterizedTest
    @ValueSource(chars = {'+', '-', '*'})
    public void isOperatorTest(char input) {
        boolean result = App.isOperator(input);
        assertEquals(true, result);
    }
    @Test
    public void isOperatorTestFalse() {
        boolean result = App.isOperator('h');
        assertEquals(false, result);
    }
    @Test
    public void isOperatorTestFalseAgain() {
        boolean result = App.isOperator('3');
        assertEquals(false, result);
    }
}
