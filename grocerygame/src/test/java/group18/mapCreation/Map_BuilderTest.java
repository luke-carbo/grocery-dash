package group18.mapCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class Map_BuilderTest {
    private Map_Builder map_builder;
    private BufferedImage image;
    private Graphics graphics;
    private Map_Tile[][] tile_map;
    private static final int TILE_SIZE = 30;

    private boolean regionHasPixels(BufferedImage image,int x, int y, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(image.getRGB(i, j) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @BeforeEach
    void setUp() {
        map_builder = new Map_Builder();
        tile_map = map_builder.getTileMap();
        image = new BufferedImage(map_builder.getCols() * TILE_SIZE,
                map_builder.getRows() * TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();

    }

    @Test
    void rowDimensionTest(){
        assertEquals(20, map_builder.getRows());
    }
    @Test
    void columnDimensionTest(){
        assertEquals(27, map_builder.getCols());
    }
    @Test
    void tileSizeTest(){
        assertEquals(30, map_builder.getTileSize());
    }

    @Test
    void testRandomWall(){
        int[] coordinates = map_builder.findTile('w');
        assertEquals(Tile_Type.Wall,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
        assertTrue(map_builder.isSolidTile(coordinates[0], coordinates[1]));
    }
    @Test
    void testRandomFloor(){
        int[] coordinates = map_builder.findTile('f');
        assertEquals(Tile_Type.Floor,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }
    @Test
    void testRandomShelf(){
        int[] coordinates = map_builder.findTile('s');
        assertEquals(Tile_Type.Shelf,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }
    @Test
    void testRandomTech(){
        int[] coordinates = map_builder.findTile('t');
        assertEquals(Tile_Type.Tech,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }
    @Test
    void testRandomMeat(){
        int[] coordinates = map_builder.findTile('m');
        assertEquals(Tile_Type.Meat,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }
    @Test
    void testRandomProduce(){
        int[] coordinates = map_builder.findTile('p');
        assertEquals(Tile_Type.Produce,map_builder.getTileMap()[coordinates[0]][coordinates[1]].getTileType());
    }
    @Test
    void testNoTile(){
        int[] coordinates = map_builder.findTile('u');
        assertNull(coordinates);
    }

    // Boundary Testing

    @Test
    void topBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(0, -1));
    }
    @Test
    void leftBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(-2, 10));
    }
    @Test
    void rightBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(100, 10));
    }
    @Test
    void bottomBoundaryOutOfBoundsTest(){
        assertTrue(map_builder.isSolid(10, 50));
    }

    // Drawing tests
    @Test
    void drawPixelsOnScreen(){
        map_builder.draw(graphics);
        assertTrue(regionHasPixels(image,0,0,TILE_SIZE,TILE_SIZE));
    }

// test
    @AfterEach
    void tearDown() {
        map_builder = null;
        tile_map = null;
        image = null;
        graphics = null;
    }

}
