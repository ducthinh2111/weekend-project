import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CodingGame4 {

    public static void main(String[] args) {
        List<String> image = List.of("DBABADCB","ACCCAABD","CBBADBBB","DDDDAAAC","DAAABBCC","DCACBDDC","DAAAABCA","BABADDAC","AAAAABAB","CAABCDDA");

        System.out.println(findLargestCircle(10, 8, image));
    }

    /**
     * @param nRows The height of the image.
     * @param nCols The width of the image.
     * @param image Pixels of the image, given row by row from top to bottom.
     * @return The parameters of the largest circle [centerRow, centerCol, radius].
     */
    public static List<Integer> findLargestCircle(int nRows, int nCols, List<String> image) {
        // Write your code here
        int largestDiameter = Math.min(nRows, nCols);
        while (largestDiameter > 0) {
            int radius = largestDiameter / 2;
            for (int i = 0; i + largestDiameter < nRows; i++) {
                for (int j = 0; j + largestDiameter < nCols; j++) {
                    int centerRow = i + radius;
                    int centerCol = j + radius;
                    if (checkCircle(centerRow, centerCol, image, radius)) {
                        return Arrays.asList(centerRow, centerCol, radius);
                    }
                }
            }
            largestDiameter--;
        }
        return null;
    }

    public static boolean checkCircle(int i, int j, List<String> image, int radius) {
        int theChar = image.get(i).charAt(j - radius);
        boolean isTheSame = true;
        int startRow = i - radius;
        int startCol = j - radius;
        int maxRow = startRow + radius * 2;
        int maxCol = startCol + radius * 2;
        for (int row = startRow; row <= maxRow; row++) {
            for (int col = startCol; col <= maxCol; col++) {
                double distance = Math.sqrt(Math.pow(row - i, 2) + Math.pow(col - j, 2));
                if (distance >= radius && distance < radius + 1) {
                    if (image.get(row).charAt(col) != theChar) {
                        isTheSame = false;
                        break;
                    }
                }
            }
        }
        return isTheSame;
    }
}
