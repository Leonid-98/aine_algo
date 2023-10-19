package Arvutipraks_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class KnightPaths {
    private static final int ROWS = 4;
    private static final int COLUMNS = 4;
    private static final int[][] MOVES = {
            {2, 1},
            {1, 2},
            {1, -2},
            {2, -1}
    };

    public static List<List<Point>> findAllKnightPaths() {
        List<List<Point>> allPaths = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            Stack<Point> stack = new Stack<>();
            stack.push(new Point(i, 0));

            while (!stack.isEmpty()) {
                Point current = stack.peek();

                if (current.y == COLUMNS - 1) {
                    // Reached the rightmost column, add the path to the result.
                    List<Point> path = new ArrayList<>(stack);
                    allPaths.add(path);
                    stack.pop();
                } else {
                    boolean moved = false;
                    for (int[] move : MOVES) {
                        int nextX = current.x + move[0];
                        int nextY = current.y + move[1];

                        if (isValidMove(nextX, nextY) && !stack.contains(new Point(nextX, nextY))) {
                            stack.push(new Point(nextX, nextY));
                            moved = true;
                            break;
                        }
                    }

                    if (!moved) {
                        stack.pop();
                    }
                }
            }
        }
        return allPaths;
    }

    private static boolean isValidMove(int x, int y) {
        return x >= 0 && x < ROWS && y >= 0 && y < COLUMNS;
    }

    public static void main(String[] args) {
        List<List<Point>> paths = findAllKnightPaths();
        for (List<Point> path : paths) {
            System.out.println(path);
        }
    }
}