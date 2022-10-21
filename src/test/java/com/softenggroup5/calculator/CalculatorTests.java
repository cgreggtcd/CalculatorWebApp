package com.softenggroup5.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculatorTests
{
    @Test
    void testCalculate() {
        assertEquals("0", Calculator.calculate(""));
    }
}