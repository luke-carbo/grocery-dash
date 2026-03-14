package group18.mapCreation;

import java.awt.*;

public class Game_Map {
    private Map_Builder map_builder;

    public Game_Map() {
        generateMap();
    }

    /**
     * Calls Map builder to generate map
     */
    public void generateMap() {
        map_builder = new Map_Builder();
    }

    public void draw(Graphics g) {
        map_builder.draw(g);
    }

    public boolean isSolid(int x, int y) {
        return map_builder.isSolid(x, y);
    }

    public boolean isSolidTile(int row, int col) {
        return map_builder.isSolidTile(row, col);
    }

    public int getRows() {
        return map_builder.getRows();
    }

    public int getCols() {
        return map_builder.getCols();
    }

    public int getTileSize() {
        return map_builder.getTileSize();
    }
}