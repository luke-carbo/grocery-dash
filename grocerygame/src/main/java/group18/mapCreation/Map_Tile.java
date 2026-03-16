package group18.mapCreation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Takes character 'c' and creates a tile type
 */
public class Map_Tile {

    private Tile_Type type;
    public final int TILE_SIZE = 30;



    private static BufferedImage shelvesImage;
    private static BufferedImage wallImage;
    private static BufferedImage floorImage;

    static {
        try {
            shelvesImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/shelves.png"));
//            shelvesImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_shelf.png"));
            wallImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_wall.png"));
//            wallImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_wall2.png"));
            floorImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_floor.png"));

        }
        catch (Exception e) {
            System.err.println("Failed to load images " + e.getMessage());
        }

    }

    Map_Tile(char c) {
        switch (c) {
            case 'w':
                type = Tile_Type.Wall;
                break;
            case 'f':
                type = Tile_Type.Floor;
                break;
            case 's':
                type = Tile_Type.Shelf;
                break;
        }
    }
    public void draw(Graphics g, int row, int column) {
        switch (type) {
            case Wall:
                g.drawImage(wallImage, column * TILE_SIZE, row * TILE_SIZE, null);
                break;
            case Floor:
                g.drawImage(floorImage, column * TILE_SIZE, row * TILE_SIZE, null);
                break;
            case Shelf:
                g.drawImage(shelvesImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
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