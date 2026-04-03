package group18.mapCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Map_Tile class.
 */
public class Map_TileTest {

    private static final int TILE_SIZE = 30;
    private BufferedImage image;
    private Graphics graphics;

    private BufferedImage blankImage() {
        return new BufferedImage(TILE_SIZE, TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
    }

    private boolean regionHasPixels(BufferedImage image, int x, int y, int w, int h) {
        for (int r = y; r < y+h; r++) {
            for (int c = x; c < x+w; c++) {
                if (image.getRGB(c,r) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets up the test environment before testing.
     */
    @BeforeEach
    void setUp() {
        image = blankImage();
        graphics = image.getGraphics();
    }


    // 1. Functional Tests
    /**
     * tests that a wall tile is created as solid and can be drawn.
     */
    @Test
    void wallTileCreationTest(){
        Map_Tile tile = new Map_Tile('w');
        assertEquals(Tile_Type.Wall, tile.getTileType());
        assertTrue(tile.isSolid());
        tile.draw(graphics,0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that a floor tile is created as non-solid and can be drawn.
     */
    @Test
    void floorTileCreationTest(){
        Map_Tile tile = new Map_Tile('f');
        assertEquals(Tile_Type.Floor, tile.getTileType());
        assertFalse(tile.isSolid());

        tile.draw(image.getGraphics(),0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that a shelf tile is created as solid and can be drawn.
     */
    @Test
    void shelfTileCreationTest(){
        Map_Tile tile = new Map_Tile('s');
        assertEquals(Tile_Type.Shelf, tile.getTileType());
        assertTrue(tile.isSolid());

        tile.draw(image.getGraphics(),0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that a tech tile is created as solid and can be drawn.
     */
    @Test
    void techTileCreationTest(){
        Map_Tile tile = new Map_Tile('t');
        assertEquals(Tile_Type.Tech, tile.getTileType());
        assertTrue(tile.isSolid());

        tile.draw(image.getGraphics(),0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that a meat tile is created as solid and can be drawn.
     */
    @Test
    void meatTileCreationTest(){
        Map_Tile tile = new Map_Tile('m');
        assertEquals(Tile_Type.Meat, tile.getTileType());
        assertTrue(tile.isSolid());

        tile.draw(image.getGraphics(),0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that a produce tile is created as solid and can be drawn.
     */
    @Test
    void produceTileCreationTest(){
        Map_Tile tile = new Map_Tile('p');
        assertEquals(Tile_Type.Produce, tile.getTileType());
        assertTrue(tile.isSolid());

        tile.draw(image.getGraphics(),0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that a blocked tile is created as solid and can be drawn.
     */
    @Test
    void blockedTileCreationTest(){
        Map_Tile tile = new Map_Tile('b');
        assertEquals(Tile_Type.Blocked, tile.getTileType());
        assertTrue(tile.isSolid());

        tile.draw(image.getGraphics(),0,0);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

    /**
     * tests that an unknown tile type does not create a valid tile type.
     */
    @Test
    void unknownTileCreationTest(){
        Map_Tile tile = new Map_Tile('u');
        assertNull(tile.getTileType());


    }

    /**
     * Cleans up the test environment after testing.
     */
    @AfterEach
    void tearDown() {
        graphics.dispose();
    }


}
