package com.starea.adventofcode;

import java.util.Objects;
import java.util.Scanner;

public class AOCDayOne {
    public static void main(String[] args) {
        String filename = "AOCDayOne.txt";
        try (Scanner scanner = new Scanner(Objects.requireNonNull(AOCDayOne.class.getClassLoader().getResourceAsStream(filename)))) {
            int sum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int extractedNumber = extractNumber(line);
                sum += extractedNumber;
            }
            System.out.println(sum);
        }
    }

    private static int extractNumber(String line) {
        String removedCharacters = line.replaceAll("[a-z|A-Z]", "");

        if (removedCharacters.isEmpty()) {
            return 0;
        }
        int numFirst = Integer.parseInt(String.valueOf(removedCharacters.charAt(0)));
        if (removedCharacters.length() == 1) {
            return numFirst * 10 + numFirst;
        } else {
            int numLast = Integer.parseInt(String.valueOf(removedCharacters.charAt(removedCharacters.length() - 1)));
            return numFirst * 10 + numLast;
        }
    }
}