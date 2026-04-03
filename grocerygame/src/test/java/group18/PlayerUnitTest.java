package group18;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Player class.
 */
public class PlayerUnitTest {

    private Player player;

    /**
     * Setup before each test
     */
    @BeforeEach
    void setUp() {
        player = new Player(60, 90);
    }

    /**
     * Test 1
     * Player should be alive immediately after construction.
     */
    @Test
    void player_isAlive_onConstruction() {
        assertTrue(player.isAlive(),
                "Player should be alive immediately after construction.");
    }

    /**
     * Test 2
     * Player should not be alive after setDead() is called.
     */
    @Test
    void player_isNotAlive_afterSetDead() {
        player.setDead();

        assertFalse(player.isAlive(),
                "Player should not be alive after setDead() is called.");
    }

    /**
     * Test 3
     * Player's initial position should match the constructor's arguments.
     */
    @Test
    void player_initialPosition_isCorrect() {
        assertEquals(60, player.getX(),
                "Player X should match the value passed to the constructor.");
        assertEquals(90, player.getY(),
                "Player Y should match the value passed to the constructor.");
    }

    /**
     * Test 4
     * Player's initial frame should be FRAME_DOWN.
     */
    @Test
    void player_currentFrame_defaultsToFrameDown() {
        assertEquals(Player.FRAME_DOWN, player.getCurrentFrame(),
                "Player's initial frame should be FRAME_DOWN.");
    }

    /**
     * Test 5
     * Player's current frame should be updated correctly using setCurrentFrame().
     */
    @Test
    void player_setCurrentFrame_updatesFrame() {
        player.setCurrentFrame(Player.FRAME_LEFT);
        assertEquals(Player.FRAME_LEFT, player.getCurrentFrame(),
                "getCurrentFrame() should return FRAME_LEFT after setCurrentFrame(FRAME_LEFT).");

        player.setCurrentFrame(Player.FRAME_UP);
        assertEquals(Player.FRAME_UP, player.getCurrentFrame(),
                "getCurrentFrame() should return FRAME_UP after setCurrentFrame(FRAME_UP).");

        player.setCurrentFrame(Player.FRAME_RIGHT);
        assertEquals(Player.FRAME_RIGHT, player.getCurrentFrame(),
                "getCurrentFrame() should return FRAME_RIGHT after setCurrentFrame(FRAME_RIGHT).");
    }
}