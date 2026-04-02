package group18.mapCreation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static javax.swing.text.StyleConstants.setBackground;

/**
 * Gets images and tile types and draws them
 */
public class Map_Tile {

    private Tile_Type type;
    public final int TILE_SIZE = 30;


    private static BufferedImage shelvesImage;
    private static BufferedImage wallImage;
    private static BufferedImage floorImage;
    private static BufferedImage techStand;
    private static BufferedImage meatImage;
    private static BufferedImage produceImage;


    static {
        try {
            shelvesImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/shelves.png"));
//            shelvesImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_shelf.png"));
            wallImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_wall.png"));
//            wallImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_wall2.png"));
            floorImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/grocery_floor.png"));
            techStand = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/desktech.png"));
            meatImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/fridge.png"));
            produceImage = ImageIO.read(Map_Tile.class.getResourceAsStream("/tiles/produce.png"));

        }
        catch (Exception e) {
            System.err.println("Failed to load images " + e.getMessage());
        }

    }

    /**
     * Assigns types based on characters from Map_Builder
     * @param c
     */
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
            case 't':
                type = Tile_Type.Tech;
                break;
            case 'm':
                type = Tile_Type.Meat;
                break;
            case 'b':
                type = Tile_Type.Blocked;
                break;
            case 'p':
                type = Tile_Type.Produce;
                break;
        }
    }

    /**
     * Draws each type of tile
     * For Shelf, Tech, Meat, and Produce we have it draw the floor first so that the floor
     * ends up as the background for the transparent parts of the objects
     * @param g
     * @param row
     * @param column
     */
    public void draw(Graphics g, int row, int column) {
        switch (type) {
            case Wall:
                g.drawImage(wallImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                break;
            case Floor:
                g.drawImage(floorImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE,  TILE_SIZE, null);
                break;
            case Shelf:
                for (int i = 0; i < 2; i++) {
                    g.drawImage(floorImage, (column + i) * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
                g.drawImage(shelvesImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE*2, TILE_SIZE*2, null);
                break;
            case Tech:
                for (int i = 0; i < 2; i++) {
                    g.drawImage(floorImage, (column + i) * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
                g.drawImage(techStand, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE * 2, TILE_SIZE * 2, null);
                break;
            case Meat:
                for (int i = 0; i < 2; i++) {
                    g.drawImage(floorImage, (column + i) * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
                g.drawImage(meatImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE*2, TILE_SIZE*2, null);
                break;
            case Produce:
                for (int i = 0; i < 2; i++) {
                    g.drawImage(floorImage, (column + i) * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
                g.drawImage(produceImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE*2, 2*TILE_SIZE, null);
                break;
            case Blocked:
                g.drawImage(floorImage, column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                break;
        }
    }

    public boolean isSolid() {
        return type.isSolid();
    }

    public Tile_Type getTileType() {
        return type;
    }



}
