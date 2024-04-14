package com.starea;

import java.util.ArrayList;
import java.util.List;

public class CodingGame5 {

    public static void main(String[] args) {
        List<String> image = List.of(
                "#.#",
                "...",
                "#.#"
        );

        System.out.println((solve(3, image)));
    }

    /**
     * @param n The size of the image
     * @param targetImage The rows of the desired image, from top to bottom
     */
    public static List<String> solve(int n, List<String> targetImage) {
        // Write your code here
        int[][] blur = new int[n][n];
        int[][] encodedImage = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blur[i][j] = 0;
                encodedImage[i][j] = targetImage.get(i).charAt(j) == '#' ? 1 : 0;
            }
        }

        String topCommand;
        List<String> commands = new ArrayList<>();

        do {
            topCommand = findTopCommand(n, encodedImage, blur);
            commands.add(topCommand);
            System.out.println("Test");
        } while (!topCommand.isBlank());

        List<String> revertedCommand = new ArrayList<>();
        for (int i = commands.size() - 2; i >= 0; i--) {
            revertedCommand.add(commands.get(i));
        }

        return revertedCommand;
    }

    public static String findTopCommand(int n, int[][] image, int[][] blur) {
        for (int i = 0; i < n ; i++) {
            int whitePixel = 0;
            boolean isSamePixelInARow = true;
            for (int j = 0; j < n; j++) {
                if (image[i][j] != whitePixel && blur[i][j] == 0) {
                    isSamePixelInARow = false;
                    break;
                }
            }

            if (isSamePixelInARow) {
                boolean isTheRowBlur = true;
                for (int j = 0; j < n; j++) {
                    if (blur[i][j] == 0) {
                        isTheRowBlur = false;
                        break;
                    }
                }

                if (!isTheRowBlur) {
                    for (int j = 0; j < n; j++) {
                        blur[i][j] = 1;
                    }
                    return "R " + i;
                }
            }
        }

        for (int j = 0; j < n ; j++) {
            int blackPixel = 1;
            boolean isSamePixelInAColumn = true;
            for (int i = 0; i < n; i++) {
                if (image[i][j] != blackPixel && blur[i][j] == 0) {
                    isSamePixelInAColumn = false;
                    break;
                }
            }

            if (isSamePixelInAColumn) {
                boolean isTheColumnBlur = true;
                for (int i = 0; i < n; i++) {
                    if (blur[i][j] == 0) {
                        isTheColumnBlur = false;
                        break;
                    }
                }

                if (!isTheColumnBlur) {
                    for (int i = 0; i < n; i++) {
                        blur[i][j] = 1;
                    }
                    return "C " + j;
                }
            }
        }

        return "";
    }
}
