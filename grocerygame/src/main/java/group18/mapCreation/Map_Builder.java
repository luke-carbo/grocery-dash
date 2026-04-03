package group18.mapCreation;

import java.awt.*;

public class Map_Builder {

    private Map_Tile[][] tile_Map;
    public final int TILE_SIZE = 30;
    private int rows;
    private int cols;

    /**
     * Creates map with all tiles
     * Iterates through a string -> calls Map_Tile to draw the images in a particular spot
     */
    public Map_Builder() {
        String[] map = {
                "wwwwwwwwwwwwwwwwwwwwwffwwww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffsbsbsbfffftbtbtbffffww",
                "wwfffbbbbbbffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwffffffffffffftbtbtbffffww",
                "wwfffsbsbsbffffbbbbbbffffww",
                "wwfffbbbbbbffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffpbpbpbffffmbmbmbffffww",
                "wwfffbbbbbbffffbbbbbbffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffpbpbpbffffmbmbmbffffww",
                "wwfffbbbbbbffffbbbbbbffffww",
                "wwfffffffffffffffffffffffww",
                "wwfffffffffffffffffffffffww",
                "wwwwwffwwwwwwwwwwwwwwwwwwww"
        };
        rows = map.length;
        cols = map[0].length();

        tile_Map = new Map_Tile[rows][cols];
        createTileMap(map);

    }

    /**
     * This draw method draws the floors and walls.
     * @param g
     */
    public void draw(Graphics g) {
        // Pass 1: draw floors and walls
        for (int i = 0; i < tile_Map.length; i++) {
            for (int j = 0; j < tile_Map[i].length; j++) {
                Tile_Type t = tile_Map[i][j].getTileType();
                if (t == Tile_Type.Floor || t == Tile_Type.Wall || t == Tile_Type.Blocked) {
                    tile_Map[i][j].draw(g, i, j);
                }
            }
        }
        // Pass 2: Draw Objects in the middle
        for (int i = 0; i < tile_Map.length; i++) {
            for (int j = 0; j < tile_Map[i].length; j++) {
                Tile_Type t = tile_Map[i][j].getTileType();
                if (t == Tile_Type.Tech || t == Tile_Type.Meat || t == Tile_Type.Shelf || t == Tile_Type.Produce) {
                    tile_Map[i][j].draw(g, i, j);
                }
            }
        }
    }

    public void createTileMap(String[] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length(); j++) {
                char c = map[i].charAt(j);
                Map_Tile tile = new Map_Tile(c);
                tile_Map[i][j] = tile;
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

    public int[] findTile(char c) {
        Map_Tile tile = new Map_Tile(c);
        for (int i = 0; i < tile_Map.length; i++) {
            for (int j = 0; j < tile_Map[i].length; j++) {
                if (tile_Map[i][j].getTileType() == tile.getTileType()) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    public Map_Tile[][] getTileMap() {
        return tile_Map;
    }

    public int getTileSize() {
        return TILE_SIZE;
    }
}
