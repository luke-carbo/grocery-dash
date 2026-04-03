package group18.mapCreation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tile_Type enum.
 */
public class Tile_TypeTest {
    /**
     * tests that wall tiles are solid.
     */
    @Test
    void wallIsSolidTest(){
        assertTrue(Tile_Type.Wall.isSolid());
    }

    /**
     * tests that floor tiles are not solid.
     */
    @Test
    void floorIsSolidTest(){
        assertFalse(Tile_Type.Floor.isSolid());
    }

    /**
     * tests that shelf tiles are solid.
     */
    @Test
    void shelfIsSolidTest(){
        assertTrue(Tile_Type.Shelf.isSolid());
    }

    /**
     * tests that tech tiles are solid.
     */
    @Test
    void techIsSolidTest(){
        assertTrue(Tile_Type.Tech.isSolid());
    }

    /**
     * tests that meat tiles are solid.
     */
    @Test
    void meatIsSolidTest(){
        assertTrue(Tile_Type.Meat.isSolid());
    }

    /**
     * tests that produce tiles are solid.
     */
    @Test
    void produceIsSolidTest(){
        assertTrue(Tile_Type.Produce.isSolid());
    }

    /**
     * tests that blocked tiles are solid.
     */
    @Test
    void blockedIsSolidTest(){
        assertTrue(Tile_Type.Blocked.isSolid());
    }




}
