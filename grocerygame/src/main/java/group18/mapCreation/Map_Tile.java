package group18.mapCreation;

import java.awt.*;

/**
 * Takes character 's' and creates a tile type
 */
public class Map_Tile {

    private Tile_Type type;
    private final int TILE_SIZE = 40;

    Map_Tile(char c) {
        switch (c) {
            case 'w':
                type = Tile_Type.Wall;
                break;
            case 'f':
                type = Tile_Type.Floor;
                break;
        }
    }
    public void draw(Graphics g, int row, int column) {
        switch (type) {
            case Wall:
                g.setColor(Color.GRAY);
                g.fillRect(column * TILE_SIZE, row * TILE_SIZE, 40, 40);
                break;
            case Floor:
                g.setColor(Color.BLUE);
                g.fillRect(column * TILE_SIZE, row * TILE_SIZE, 40, 40);
                break;

        }
    }

    public boolean isSolid() {
        return type != null && type.isSolid();
    }

    public Tile_Type getTileType() {
        return type;
    }
}