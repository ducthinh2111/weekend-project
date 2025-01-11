import java.util.Random;

public class Main {
    
    private static final Random rand = new Random();
    
    public static void main(String[] args) {
        int rows = 20;
        int cols = 20;
        int startR = 0;
        int startC = 1;
        int endR = 3;
        int endC = 19;
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
        
        int numberOfRandomPoints = 10;
        int currentR = startR;
        int currentC = startC;
        while (numberOfRandomPoints-- > 0) {
            int maxR = rows - 2;
            int min = 1;
            int randomR = rand.nextInt(maxR - min + 1) + min;
            int maxC = cols - 2;
            int randomC = rand.nextInt(maxC - min + 1) + min;
            go(currentR, currentC, randomR, randomC, rows, cols, maze);
            currentR = randomR;
            currentC = randomC;
        }
        go(currentR, currentC, endR, endC, rows, cols, maze);

        System.out.println(rows + " " + cols + " " + startC + " " + startR + " " + endC + " " + endR);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
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
        
        boolean isAllowedToGoHorizontal = 
                startR != 0 && 
                startR != rows - 1 &&
                startC + (endC - startC) != 0 &&
                startC + (endC - startC) != cols - 1;
        boolean isAllowedToGoVertical = 
                startC != 0 && 
                startC != rows - 1 &&
                startR + (endR - startR) != 0 &&
                startR + (endR - startR) != rows - 1;
        
        if (isAllowedToGoVertical && isAllowedToGoHorizontal) {
            boolean isGoVertical = rand.nextBoolean();
            if (isGoVertical) {
                int[] currentPos = goVertical(startR, startC, endR, endC, maze);
                goHorizontal(currentPos[0], currentPos[1], endR, endC, maze);
            } else {
                int[] currentPos = goHorizontal(startR, startC, endR, endC, maze);
                goVertical(currentPos[0], currentPos[1], endR, endC, maze);
            }
            return;
        } 
        if (isAllowedToGoVertical) {
            int[] currentPos = goVertical(startR, startC, endR, endC, maze);
            goHorizontal(currentPos[0], currentPos[1], endR, endC, maze);
            return;
        } 
        if (isAllowedToGoHorizontal) {
            int[] currentPos = goHorizontal(startR, startC, endR, endC, maze);
            goVertical(currentPos[0], currentPos[1], endR, endC, maze);
        }
    }
    
    private static int[] goVertical(int startR, int startC, int endR, int endC, int[][] maze) {
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

    private static int[] goHorizontal(int startR, int startC, int endR, int endC, int[][] maze) {
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