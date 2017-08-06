package com.profetadabola.tools;

/**
 * Created by rafa on 30/07/17.
 */

public class InputValidator {

    public static boolean isInputEmpty(String input) {
        return input == null || input.trim().equalsIgnoreCase("");
    }
}
