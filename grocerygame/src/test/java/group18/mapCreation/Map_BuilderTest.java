package group18.mapCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Map_BuilderTest {
    private Map_Builder map_builder;

    @BeforeEach
    void setUp() {
        map_builder = new Map_Builder();
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


    @AfterEach
    void tearDown() {
        map_builder = null;
    }

}
