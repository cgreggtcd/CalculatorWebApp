package com.softenggroup5.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    // This returns a string so that errors, ints, and doubles can be returned from the same function.
    // Lower level functions can return different types. This is the only one which should be called outside testing.
    public static String calculate(String input){
        return "0";
    }

    public static boolean isValid(String input){
        // Check if input starts or ends with an invalid operator
        ArrayList<Character> invalidStartingOps = new ArrayList<>(Arrays.asList('*', '/', '^'));
        ArrayList<Character> invalidEndingOps = new ArrayList<>(Arrays.asList('+', '-', '*', '/', '^'));
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
        Pattern operatorBracket = Pattern.compile("([(][*/^])|(([+*/^]|-)[)])");
        matcher = operatorBracket.matcher(input);
        if (matcher.find()){
            return false;
        }

        // Check for any time a decimal point (.) does not have numbers on both sides
        Pattern decimalAlone = Pattern.compile("[^\\d]\\.[^\\d]");
        matcher = decimalAlone.matcher(input);
        if (matcher.find() || input.equals(".")){
            return false;
        }

        if (!validAlpha(input)){
            return false;
        }

        if(!validBrackets(input)){
            return false;
        }

        return true;
    }

    private static boolean validBrackets(String input){
        int openBracketsCount = 0;
        int closeBracketsCount = 0;
        for (int index = 0; index < input.length(); index++){
            if (input.charAt(index) == '('){
                openBracketsCount++;
            } else if (input.charAt(index) == ')') {
                closeBracketsCount++;
            }
        }
        return openBracketsCount == closeBracketsCount;
    }

    private static boolean validAlpha(String input){
        Pattern alpha = Pattern.compile("[a-zA-Z]");
        Matcher matcher = alpha.matcher(input);
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
        return true;
    }

    private static boolean checkStringNotAtIndex(String input, String match, int index) {
        String potentialMatch = input.substring(index, index+match.length());
        return !match.equals(potentialMatch);
    }
}
