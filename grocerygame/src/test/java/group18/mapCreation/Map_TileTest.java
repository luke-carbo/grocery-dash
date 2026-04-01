package group18.mapCreation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Map_TileTest {
    @Test
    void wallTileCreationTest(){
        Map_Tile tile = new Map_Tile('w');
        assertEquals(Tile_Type.Wall, tile.getTileType());
        assertTrue(tile.isSolid());
    }
    @Test
    void floorTileCreationTest(){
        Map_Tile tile = new Map_Tile('f');
        assertEquals(Tile_Type.Floor, tile.getTileType());
        assertFalse(tile.isSolid());
    }
    @Test
    void shelfTileCreationTest(){
        Map_Tile tile = new Map_Tile('s');
        assertEquals(Tile_Type.Shelf, tile.getTileType());
        assertTrue(tile.isSolid());
    }
    @Test
    void techTileCreationTest(){
        Map_Tile tile = new Map_Tile('t');
        assertEquals(Tile_Type.Tech, tile.getTileType());
        assertTrue(tile.isSolid());
    }
    @Test
    void meatTileCreationTest(){
        Map_Tile tile = new Map_Tile('m');
        assertEquals(Tile_Type.Meat, tile.getTileType());
        assertTrue(tile.isSolid());
    }
    @Test
    void produceTileCreationTest(){
        Map_Tile tile = new Map_Tile('p');
        assertEquals(Tile_Type.Produce, tile.getTileType());
        assertTrue(tile.isSolid());
    }
    @Test
    void blockedTileCreationTest(){
        Map_Tile tile = new Map_Tile('b');
        assertEquals(Tile_Type.Blocked, tile.getTileType());
        assertTrue(tile.isSolid());
    }



}
