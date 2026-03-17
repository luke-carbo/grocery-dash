package group18.ai;

import group18.mapCreation.Game_Map;

import java.awt.Point;
import java.util.*;

/** this utility finds the next movement step from a start point to a target tile. */
public class Pathfinder {

    /** this runs a grid search and returns the next world-space step toward the target. */
    public static Point getNextStep(Game_Map map, int startX, int startY, int targetX, int targetY) {

        int tileSize = map.getTileSize();

        int startCol = startX / tileSize;
        int startRow = startY / tileSize;

        int targetCol = targetX / tileSize;
        int targetRow = targetY / tileSize;

        int rows = map.getRows();
        int cols = map.getCols();

        boolean[][] visited = new boolean[rows][cols];
        Point[][] parent = new Point[rows][cols];

        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(startCol, startRow));
        visited[startRow][startCol] = true;

        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            Point current = queue.poll();

            int col = current.x;
            int row = current.y;

            if (row == targetRow && col == targetCol) {
                break;
            }

            for (int i = 0; i < 4; i++) {

                int newRow = row + dRow[i];
                int newCol = col + dCol[i];

                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols)
                    continue;

                if (visited[newRow][newCol])
                    continue;

                if (map.isSolidTile(newRow, newCol))
                    continue;

                visited[newRow][newCol] = true;
                parent[newRow][newCol] = new Point(col, row);

                queue.add(new Point(newCol, newRow));
            }
        }

        if (!visited[targetRow][targetCol]) {
            return null;
        }

        List<Point> path = new ArrayList<>();

        Point step = new Point(targetCol, targetRow);

        while (step != null && !(step.x == startCol && step.y == startRow)) {

            path.add(step);
            step = parent[step.y][step.x];

        }

        Collections.reverse(path);

        if (path.isEmpty())
            return null;

        Point nextTile = path.get(0);

        return new Point(nextTile.x * tileSize, nextTile.y * tileSize);
    }
}
