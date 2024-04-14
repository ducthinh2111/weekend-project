package com.starea.codinggame;

import java.util.List;
import java.util.Scanner;

public class CodingGame2 {

    public static void main(String[] args) {
        int n = 10;
        char[][] matrix = new char[n][n];
        // initial state
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = '.';
            }
        }

        printMatrix(matrix, n);
    }


    public static List<Integer> buildingHeights() {
        // Write your code here
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }

        char[][] matrix = new char[n][n];
        // initial state
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = '.';
            }
        }

        // game loop
        while (true) {
            String command = in.nextLine();
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("C")) {
                int colNumber = Integer.parseInt(splitCommand[1]);
                for (int i = 0; i < n; i++) {
                    matrix[i][colNumber] = '#';
                }
            } else {
                int rowNumber = Integer.parseInt(splitCommand[1]);
                for (int j = 0; j < n; j++) {
                    matrix[rowNumber][j] = '.';
                }
            }
            printMatrix(matrix, n);
        }
    }

    public static void printMatrix(char[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
