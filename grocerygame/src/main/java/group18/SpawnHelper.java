package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/** Shared utility for item spawning logic. */
public class SpawnHelper {

    /**
     * generates a random valid position on the map.
     * a valid position is a non-solid (walkable) tile.
     *
     * @param map the game map to search
     * @return pixel coordinates of a valid tile
     */
    public static Point randomMapTile(Map_Builder map, Set<Point> occupied) {
        int tileSize = map.getTileSize();

        while (true) {
            int row = (int)(Math.random() * map.getRows());
            int col = (int)(Math.random() * map.getCols());

            if (!map.isSolidTile(row, col)) {
                Point candidate = new Point(col * tileSize, row * tileSize);
                if (!occupied.contains(candidate)) {
                    return candidate;
                }
            }
        }
    }

    public static Set<Point> occupiedPoints(List<? extends Item> items) {
        Set<Point> points = new HashSet<>();
        for (Item item : items) {
            if (!item.collected) {
                points.add(new Point(item.getX(), item.getY()));
            }
        }
        return points;
    }

    /**
     * Generates a random Item Value within a predefined range.
     * <p>
     * @return a random Item Value
     */
    public static int randomValue() {
        // Random Roll for Spawn Value
        return (int) (Math.random() * 10 + 1); // Produces 0 - 10
    }
}
