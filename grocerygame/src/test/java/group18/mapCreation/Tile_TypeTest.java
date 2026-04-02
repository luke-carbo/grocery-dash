package group18.mapCreation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Tile_TypeTest {
    @Test
    void wallIsSolidTest(){
        assertTrue(Tile_Type.Wall.isSolid());
    }
    @Test
    void floorIsSolidTest(){
        assertFalse(Tile_Type.Floor.isSolid());
    }
    @Test
    void shelfIsSolidTest(){
        assertTrue(Tile_Type.Shelf.isSolid());
    }
    @Test
    void techIsSolidTest(){
        assertTrue(Tile_Type.Tech.isSolid());
    }
    @Test
    void meatIsSolidTest(){
        assertTrue(Tile_Type.Meat.isSolid());
    }
    @Test
    void produceIsSolidTest(){
        assertTrue(Tile_Type.Produce.isSolid());
    }
    @Test
    void blockedIsSolidTest(){
        assertTrue(Tile_Type.Blocked.isSolid());
    }




}
