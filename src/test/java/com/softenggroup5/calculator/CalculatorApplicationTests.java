package com.softenggroup5.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
 
public class CalculatorApplicationTests
{
    @Test
    void contextLoads() {
    }

    @Test
    void testCalculate() {
        assertEquals(0, CalculatorApplication.calculate(""));
    }
}
