package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;

/** Shared utility for item spawning logic. */
public class SpawnHelper {

    /**
     * generates a random valid position on the map.
     * a valid position is a non-solid (walkable) tile.
     *
     * @param map the game map to search
     * @return pixel coordinates of a valid tile
     */
    public static Point randomMapTile(Map_Builder map) {
        int tileSize = map.getTileSize();

        while (true) {
            int row = (int)(Math.random() * map.getRows());
            int col = (int)(Math.random() * map.getCols());

            if (!map.isSolidTile(row, col)) {
                return new Point(col * tileSize, row * tileSize);
            }
        }
    }
}