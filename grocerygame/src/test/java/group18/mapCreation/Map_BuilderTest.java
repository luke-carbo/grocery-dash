package group18.mapCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Map_Builder class.
 */
public class Map_BuilderTest {
    private Map_Builder map_builder;
    private BufferedImage image;
    private Graphics graphics;
    private Map_Tile[][] tile_map;
    private static final int TILE_SIZE = 30;

    private boolean regionHasPixels(BufferedImage image) {
        for (int i = 0; i < TILE_SIZE; i++) {
            for (int j = 0; j < TILE_SIZE; j++) {
                if(image.getRGB(i, j) != 0) {
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
        map_builder = new Map_Builder();
        tile_map = map_builder.getTileMap();
        image = new BufferedImage(map_builder.getCols() * TILE_SIZE,
                map_builder.getRows() * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();

    }

    /**
     * tests that the map has the correct row dimension.
     */
    @Test
    void rowDimensionTest(){
        assertEquals(20, map_builder.getRows());
    }

    /**
     * tests that the map has the correct column dimension.
     */
    @Test
    void columnDimensionTest(){
        assertEquals(27, map_builder.getCols());
    }

    /**
     * tests that the map uses the correct tile size.
     */
    @Test
    void tileSizeTest(){
        assertEquals(30, map_builder.getTileSize());
    }

    /**
     * tests that a random wall tile can be found and is marked solid.
     */
    @Test
    void testRandomWall(){
        int[] coordinates = map_builder.findTile('w');
        assertEquals(Tile_Type.Wall,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
        assertTrue(map_builder.isSolidTile(coordinates[0], coordinates[1]));
    }

    /**
     * tests that a random floor tile can be found.
     */
    @Test
    void testRandomFloor(){
        int[] coordinates = map_builder.findTile('f');
        assertEquals(Tile_Type.Floor,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }

    /**
     * tests that a random shelf tile can be found.
     */
    @Test
    void testRandomShelf(){
        int[] coordinates = map_builder.findTile('s');
        assertEquals(Tile_Type.Shelf,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }

    /**
     * tests that a random tech tile can be found.
     */
    @Test
    void testRandomTech(){
        int[] coordinates = map_builder.findTile('t');
        assertEquals(Tile_Type.Tech,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }

    /**
     * tests that a random meat tile can be found.
     */
    @Test
    void testRandomMeat(){
        int[] coordinates = map_builder.findTile('m');
        assertEquals(Tile_Type.Meat,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }

    /**
     * tests that a random produce tile can be found.
     */
    @Test
    void testRandomProduce(){
        int[] coordinates = map_builder.findTile('p');
        assertEquals(Tile_Type.Produce,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }

    /**
     * tests that searching for an invalid tile type returns null.
     */
    @Test
    void testNoTile(){
        int[] coordinates = map_builder.findTile('u');
        assertNull(coordinates);
    }

    // Boundary Testing
    /**
     * tests that the top boundary is treated as solid when out of bounds.
     */
    @Test
    void topBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(0, -1));
    }

    /**
     * tests that the left boundary is treated as solid when out of bounds.
     */
    @Test
    void leftBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(-2, 10));
    }

    /**
     * tests that the right boundary is treated as solid when out of bounds.
     */
    @Test
    void rightBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(100, 10));
    }

    /**
     * tests that the bottom boundary is treated as solid when out of bounds.
     */
    @Test
    void bottomBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(10, 50));
    }

    // Drawing tests
    /**
     * tests that drawing the map produces pixels on the image.
     */
    @Test
    void drawPixelsOnScreen(){
        map_builder.draw(graphics);
        assertTrue(regionHasPixels(image));
    }

// test

    /**
     * Cleans up the test environment after testing
     */
    @AfterEach
    void tearDown() {
        map_builder = null;
        tile_map = null;
        image = null;
        graphics = null;
    }

}
