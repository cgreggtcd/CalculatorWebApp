package com.softenggroup5.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    // This returns a string so that errors, ints, and doubles can be returned from the same function.
    // Lower level functions can return different types. This is the only one which should be called outside testing.
    public static String calculate(String input){
        if (!isValid(input)){
            return "That input is not valid. Please try again.";
        }
        else{
            String postfix = convertToPostfix(input);
            return evaluatePostfix(postfix);
        }
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
        for (int i = 0; i < infix.length(); i++) { //3.0+4.5-21.1
            char c = infix.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                postfix.append(c);
            } else if (isOperator(c)) {
                if(c == '-'){
                    int s = i-1;
                    if(s>-1) {
                        if (isOperator(infix.charAt(s)) || infix.charAt(s) == '('){
                            postfix.append(c);
                            continue;
                        }
                    }
                    else {
                        postfix.append(c);
                        continue;
                    }
                }
                postfix.append(' ');
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {   //while top of stack has higher precedence
                    postfix.append(stack.pop());                                          // and associativity operators
                    postfix.append(' ');
                }
                stack.push(c);
            }
            else if (c == '(') stack.push(c);
            else if (c == ')'){
                while(!stack.isEmpty() && stack.peek()!= '('){
                    postfix.append(' ');
                    postfix.append(stack.pop());
                }
                stack.pop();
            }
        }
        postfix.append(' ');
        while (!stack.isEmpty()) {
            if(stack.peek() == '(') return "Error!";
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
    public static int precedence(char c) {
        if (c == '+' | c == '-') return 1;
        else if(c == '*' || c == '/') return 2;
        else return -1;
    }

    /*
    This method evaluates the postfix string. Whenever we encounter an operand it it pushed into a stack. Whenever an operator is
    encountered, we pop off two operands from the stack and evaluate the result using the specfied operator. This result is then
    pushed onto the stack. After we have looped through the string, we pop off the last item on the stack since this is our answer/
     */
    public static String evaluatePostfix(String postfix) {
        Stack<Float> stack = new Stack<>();
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if(c == '-'){
                int s = i+1;
                if(Character.isDigit(postfix.charAt(s))){
                    continue;
                }
            }
            if (isOperator(c)) {
                float op2 = stack.pop();
                float op1 = stack.pop();
                float result = 0.0F;

                if (c == '-') result = op1 - op2;
                else if (c == '/') result = op1 / op2;
                else if (c == '+') result = op1 + op2;
                else if (c == '*') result = op1 * op2;
                stack.push(result);                                        //Push result onto stack
            }
            else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                int s = i-1;
                if(s>-1) {
                    if (postfix.charAt(s) == '-') sb.append('-');
                }
                while (postfix.charAt(i) != ' ') {                           //Reads multidigit numbers as one operand
                    sb.append(postfix.charAt(i));
                    i++;
                }
                stack.push(Float.parseFloat(sb.toString()));               //Multidigit operand pushed onto stack
            }
        }
        DecimalFormat df = new DecimalFormat("#.###");
        return Float.toString(Float.parseFloat(df.format(stack.pop())));
    }
    /*
    Checks if character is one of the three operators
    */
    public static boolean isOperator(char c) {
        if (c == '-' || c == '/' || c == '+' || c == '*') return true;
        else return false;
    }
    public static boolean isValid(String input){
        // Check if input starts or ends with an invalid operator
        ArrayList<Character> invalidStartingOps = new ArrayList<>(Arrays.asList('*', '/', '^'));
        ArrayList<Character> invalidEndingOps = new ArrayList<>(Arrays.asList('+', '-', '*', '/'));//, '^')); //uncomment this when adding power
        if (invalidStartingOps.contains(input.charAt(0)) || invalidEndingOps.contains(input.charAt(input.length()-1))){
            return false;
        }

        // Check for double operators/.
        // The expressions which would be invalid do not include +-, *-, /-, ^-
        Pattern doubleOperator = Pattern.compile("([+*/^]|-)[+*/^]");
        Matcher matcher = doubleOperator.matcher(input);
        if (matcher.find() || input.contains("--") || input.contains("..")){
            return false;
        }

        // Check for any time an open bracket is followed by *, /, or ^,
        // and any time a closed bracket is preceded by +, - , *, /, or ^
        // Uncomment this when adding brackets
        //Pattern operatorBracket = Pattern.compile("([(][*/^])|(([+*/^]|-)[)])");
        /*matcher = operatorBracket.matcher(input);
        if (matcher.find()){
            return false;
        }
        */

        // Check for any time a decimal point (.) does not have numbers on both sides
        Pattern decimalAlone = Pattern.compile("[^\\d]\\.[^\\d]");
        matcher = decimalAlone.matcher(input);
        if (matcher.find() || input.equals(".")){
            return false;
        }

        if (!validAlpha(input)){
            return false;
        }

        return validBrackets(input);
    }

    private static boolean validBrackets(String input){
        // Uncomment this when adding bracket functionality
        /*int openBracketsCount = 0;
        int closeBracketsCount = 0;
        for (int index = 0; index < input.length(); index++){
            if (input.charAt(index) == '('){
                openBracketsCount++;
            } else if (input.charAt(index) == ')') {
                closeBracketsCount++;
            }
        }
        return openBracketsCount == closeBracketsCount;*/

        //Remove this when adding bracket functionality
        Pattern bracket = Pattern.compile("[()]");
        Matcher matcher = bracket.matcher(input);
        return !matcher.find();
    }

    private static boolean validAlpha(String input){
        Pattern alpha = Pattern.compile("[a-zA-Z]");
        Matcher matcher = alpha.matcher(input);
        /*
        while (matcher.find()){
            if (matcher.group().equals("e")){
                if (checkStringNotAtIndex(input, "exp(", matcher.start())) {
                    return false;
                }
                // Check if it is exp(<anything other than number or (>
                String nextChar = Character.toString(input.charAt(matcher.start() + 4));
                Pattern invalidChar = Pattern.compile("[^0-9(]");
                Matcher charMatcher = invalidChar.matcher(nextChar);
                if (charMatcher.find()){
                    return false;
                }
                // skip x and p
                for(int i = 0; i < 2; i++){
                    matcher.find();
                }
            }
            else if (matcher.group().equals("l")){
                if (checkStringNotAtIndex(input, "log(", matcher.start())) {
                    return false;
                }
                // Check if it is log(<anything other than number or (>
                String nextChar = Character.toString(input.charAt(matcher.start() + 4));
                Pattern invalidChar = Pattern.compile("[^0-9(]");
                Matcher charMatcher = invalidChar.matcher(nextChar);
                if (charMatcher.find()){
                    return false;
                }
                // skip o and g
                for(int i = 0; i < 2; i++){
                    matcher.find();
                }
            }
            else {
                return false;
            }
        }
        return true;*/
        return !matcher.find();
    }

    private static boolean checkStringNotAtIndex(String input, String match, int index) {
        String potentialMatch = input.substring(index, index+match.length());
        return !match.equals(potentialMatch);
    }
}