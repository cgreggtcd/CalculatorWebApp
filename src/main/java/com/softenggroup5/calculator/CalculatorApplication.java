package com.softenggroup5.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.Stack;

@SpringBootApplication
public class CalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
        boolean exit = false;
        while (!exit) {
            System.out.println("Enter the numeric expression you would like to evaluate: ");
            Scanner input = new Scanner(System.in);
            String expression = input.next();
            if (isValid(expression)) {
                String postfix = convertToPostfix(expression);
                System.out.println("The expression evaluates to: " + evaluatePostfix(postfix));
            } else if (expression.equals("exit")) {
                exit = true;
            }
            else {
                System.out.println("Invalid expression to evaluate");
                System.out.println("Type 'exit' if you would like to leave the calculator, if not enter a string ");
            }
        }
    }
    /*
    This method checks the validity of the input. It returns false for an invalid expression.
    This might be an expression which contains spaces, characters other than the digits and the three operators,
    and also an expression which is not written in infix (operand-operator-operand)
    */
    public static boolean isValid(String exp) {
        int n = exp.length() - 1;
        if (isOperator(exp.charAt(0))) return false;             //Return false if operator is at start of string
        char lastChar = '1';                                     //Will store the last character encountered when looping through string
        for (char c : exp.toCharArray()) {
            if (Character.isDigit(c)) {
                lastChar = c;
            } else if (isOperator(c)) {
                if (isOperator(lastChar)) return false;          //If another operator appeared before this operator return false
                else lastChar = c;
            } else return false;
        }
        if (isOperator(exp.charAt(n))) return false;
        return true;
    }
    /*
    This method will convert the infix expression inputted by the user into postfix notation.
    Due to multidigit numbers, we will have to include spaces to distinguish every operand and operator.
    To deduct the order of operators in the postfix string we will have to initially push an operator onto a stack and whenever
    another operator is encountered, we check if the top of the stack has greater precedence and associativity than the current operator, and
    if so, we keep popping off the operators at the top of the stack and appending them onto the string until the stack is empty or we
    we encounter an operator which does not have greater precedence or associativity. If we encounter such an operator we push it onto the stack.
    At the end of our string we pop any operators off the stack and append to the string.
     */
    public static String convertToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (isOperator(c)) {
                postfix.append(' ');                                                      //Adding spaces
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {   //while top of stack has higher precedence
                    postfix.append(stack.pop());                                          // and associativity operators
                    postfix.append(' ');
                }
                stack.push(c);
            }
        }
        postfix.append(' ');
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
            postfix.append(' ');
        }
        return postfix.toString();
    }
    /*
    Deduces the precedence of operators. Multiplication has a precedence of 2, whereas addition and subtraction have a precedence of 1.
    If '+' is on top of stack and '-' is the current operator, we will pop off '+' and append it to the string since associativity of these
    two operators is read from left to right and '+' we encountered first in the infix string.
    */
    public static int precedence(char c){
        if (c == '+' | c == '-' ) return 1;
        else return 2;
    }
    /*
    This method evaluates the postfix string. Whenever we encounter an operand it it pushed into a stack. Whenever an operator is
    encountered, we pop off two operands from the stack and evaluate the result using the specfied operator. This result is then
    pushed onto the stack. After we have looped through the string, we pop off the last item on the stack since this is our answer/
     */
    public static String evaluatePostfix(String postfix){
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < postfix.length(); i++){
            char c = postfix.charAt(i);
            if(isOperator(c)){
                int op2 = stack.pop();
                int op1 = stack.pop();
                int result;

                if(c == '+') result = op1 + op2;
                else if(c == '-') result = op1 - op2;
                else result = op1 * op2;
                stack.push(result);                                        //Push result onto stack
            }
            else if(Character.isDigit(c)){
                StringBuilder sb = new StringBuilder();
                while(postfix.charAt(i) != ' '){                           //Reads multidigit numbers as one operand
                    sb.append(postfix.charAt(i));
                    i++;
                }
                stack.push(Integer.parseInt(sb.toString()));               //Multidigit operand pushed onto stack
            }
        }
        return Integer.toString(stack.pop());
    }
    /*
    Checks if character is one of the three operators
    */
    public static boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*') return true;
        else return false;
    }
}
