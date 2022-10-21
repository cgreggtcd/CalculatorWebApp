package com.softenggroup5.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    // This returns a string so that errors, ints, and doubles can be returned from the same function.
    // Lower level functions can return different types. This is the only one which should be called outside testing.
    public static String calculate(String input){
        return "0";
    }

    public static boolean isValid(String input){
        if (!validAlpha(input)){
            return false;
        }
        return true;
    }

    private static boolean validAlpha(String input){
        Pattern alpha = Pattern.compile("[a-zA-Z]");
        Matcher matcher = alpha.matcher(input);
        while (matcher.find()){
            if (matcher.group().equals("e")){
                if (checkStringNotAtIndex(input, "exp(", matcher.start())) {
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
