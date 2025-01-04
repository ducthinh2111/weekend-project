import java.util.Random;

public class Main {
    
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        int startR = 0;
        int startC = 1;
        int endR = 3;
        int endC = 9;
        int[][] maze = new int[rows][cols];
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isGate(row, col, startR, startC, endR, endC)) {
                    maze[row][col] = 0;
                } else {
                    maze[row][col] = 1;
                }
            }
        }
        
        int numberOfRoutes = 1;
        while (numberOfRoutes-- > 0) {
            int numberOfRandomPoints = 3;
            int currentR = startR;
            int currentC = startC;
            while (numberOfRandomPoints-- > 0) {
                int randomR = (int) ((Math.random() * (rows - 1 - 1)) + 1);
                int randomC = (int) ((Math.random() * (cols - 1 - 1)) + 1);
                System.out.println("Random point: " + randomR + " " + randomC);
                go(currentR, currentC, randomR, randomC, rows, cols, maze);
                currentR = randomR;
                currentC = randomC;
            }
            go(currentR, currentC, endR, endC, rows, cols, maze);
        }
        
        for (int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 1) {
                    System.out.print("*" + " ");
                } else {
                    System.out.print("." + " ");
                }
            }
        }
    }

    private static boolean isGate(int row, int col, int startR, int startC, int endR, int endC) {
        return (row == startR && col == startC) || 
                (row == endR && col == endC);
    }
    
    private static void go(int startR, 
                           int startC, 
                           int endR, 
                           int endC,
                           int rows,
                           int cols,
                           int[][] maze) {
        boolean isAllowedToGoByRow = startC != 0 && startC != cols - 1;
        boolean isAllowedToGoByCol = startR != 0 && startR != rows - 1;
        
        if (isAllowedToGoByRow && isAllowedToGoByCol) {
            Random rand = new Random();
            boolean goRowFirst = rand.nextBoolean();
            if (goRowFirst) {
                int[] currentPos = goRow(startR, startC, endR, endC, maze);
                goCol(currentPos[0], currentPos[1], endR, endC, maze);
            } else {
                int[] currentPos = goCol(startR, startC, endR, endC, maze);
                goRow(currentPos[0], currentPos[1], endR, endC, maze);
            }
        } else if (isAllowedToGoByRow) {
            int[] currentPos = goRow(startR, startC, endR, endC, maze);
            goCol(currentPos[0], currentPos[1], endR, endC, maze);
        } else {
            int[] currentPos = goCol(startR, startC, endR, endC, maze);
            goRow(currentPos[0], currentPos[1], endR, endC, maze);
        }
    }
    
    private static int[] goRow(int startR, int startC, int endR, int endC, int[][] maze) {
        while (startR != endR) {
            if (startR < endR) {
                startR++;
                maze[startR][startC] = 0;
            }
            if (startR > endR) {
                startR--;
                maze[startR][startC] = 0;
            }
        }
        return new int[]{ startR, startC };
    }

    private static int[] goCol(int startR, int startC, int endR, int endC, int[][] maze) {
        while (startC != endC) {
            if (startC < endC) {
                startC++;
                maze[startR][startC] = 0;
            }
            if (startC > endC) {
                startC--;
                maze[startR][startC] = 0;
            }
        }
        return new int[]{ startR, startC };
    }
}