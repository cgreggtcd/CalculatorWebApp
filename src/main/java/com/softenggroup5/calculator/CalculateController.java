package com.softenggroup5.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalculateController {
    private String solution = "Please enter a string above to calculate.";

    @PostMapping("/calculator")
    public String calculatorForm(String expression) {
        solution = Calculator.calculate(expression);
        return "redirect:/calculator";
    }

    @GetMapping("/calculator")
    public String calculatorSubmit(Model model) {
        model.addAttribute("solution", solution);
        return "calculator";
    }
}

