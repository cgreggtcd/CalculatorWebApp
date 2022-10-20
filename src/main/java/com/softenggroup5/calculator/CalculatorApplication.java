package com.softenggroup5.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    public static double calculate(String input){
        return 0;
    }

}

class Calculation {
    private String expression;

    public Double getSolution() {
        return CalculatorApplication.calculate(expression);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

@Controller
class CalculateController {
    private List<String> solutions = new ArrayList<>();
    private double solution = Double.NaN;

    @PostMapping("/calculator")
    public String calculatorForm(String expression) {
        solution = CalculatorApplication.calculate(expression);
        solutions.add(Double.toString(solution));
        return "redirect:/calculator";
    }

    @GetMapping("/calculator")
    public String calculatorSubmit(Model model) {
        model.addAttribute("solution", solution);
        System.out.println("Solution: "+ solution);
        return "calculator";
    }
}
