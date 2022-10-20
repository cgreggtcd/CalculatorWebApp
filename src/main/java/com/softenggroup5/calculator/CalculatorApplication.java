package com.softenggroup5.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


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

    @GetMapping("/calculator")
    public String calculatorForm(Model model) {
        model.addAttribute("calculator", new Calculation());
        return "calculator";
    }

    @PostMapping("/calculator")
    public String greetingSubmit(@ModelAttribute Calculation calculation, Model model) {
        model.addAttribute("calculator", calculation);
        return "result";
    }
}
