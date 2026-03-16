package group18.mapCreation;

import java.awt.*;

public class Map_Builder {

    private Map_Tile[][] tile_Map;
    public final int TILE_SIZE = 30;
    private int rows;
    private int cols;

    /**
     * Creates map with all tiles
     * Iterates through a string -> displays graphics in correct position
     */
    public Map_Builder() {
        String[] map = {
                "wwwwwwwwwwwwwwwwwwwwwwwwwww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwffffsssssffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwffffsssssffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwffffsssssffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwwwwffwwwwwwwwwwwwwffwwwww"
        };

        rows = map.length;
        cols = map[0].length();

        tile_Map = new Map_Tile[rows][cols];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length(); j++) {
                char c = map[i].charAt(j);
                Map_Tile tile = new Map_Tile(c);
                tile_Map[i][j] = tile;
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < tile_Map.length; i++) {
            for (int j = 0; j < tile_Map[i].length; j++) {
                tile_Map[i][j].draw(g, i, j);
            }
        }
    }

    public boolean isSolid(int x, int y) {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;
        if (col < 0 || col >= cols || row < 0 || row >= rows) {
            return true;
        }
        return tile_Map[row][col].isSolid();
    }

    public boolean isSolidTile(int row, int col) {
        if (col < 0 || col >= cols || row < 0 || row >= rows) {
            return true;
        }
        return tile_Map[row][col].isSolid();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }
}