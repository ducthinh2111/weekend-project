package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {

    private static final Pattern functionPattern = Pattern.compile("print\\((.*)\\)");
    private static final Pattern stringPattern = Pattern.compile("\"(.*)\"");

    public static void main(String[] args) throws IOException {
        System.out.println(new File(args[0]).getAbsolutePath());
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line = reader.readLine();
            parse(line);
        }
    }


    private static List<String> parse(String line) {
        Matcher functionMatcher = functionPattern.matcher(line);
        if (functionMatcher.matches()) {
            Matcher stringMatcher = stringPattern.matcher(functionMatcher.group(1));
            if (stringMatcher.matches()) {
                System.out.println(stringMatcher.group(1));
            }
        }
        return List.of();
    }
}
