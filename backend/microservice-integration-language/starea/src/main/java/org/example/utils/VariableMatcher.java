package org.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableMatcher {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\$(.*)");
    
    public static Matcher matcher(String input) {
        return VARIABLE_PATTERN.matcher(input);
    }
}
