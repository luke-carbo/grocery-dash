package group18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameStateResetHelper class.
 */
public class GameStateResetHelperTest {

    /**
     * tests that reset state sets the default player position correctly.
     */
    @Test
    void createResetState_setsDefaultPlayerPositionCorrectly() {
        GameStateResetHelper.ResetStateData data =
                GameStateResetHelper.createResetState(Player.FRAME_DOWN);

        assertEquals(165, data.playerX, "Player X should reset to the default starting position.");
        assertEquals(730, data.playerY, "Player Y should reset to the default starting position.");
    }

    /**
     * tests that reset state sets the default game flags to false.
     */
    @Test
    void createResetState_setsDefaultGameFlagsToFalse() {
        GameStateResetHelper.ResetStateData data =
                GameStateResetHelper.createResetState(Player.FRAME_DOWN);

        assertFalse(data.gameOver, "gameOver should be false after reset.");
        assertFalse(data.gameWon, "gameWon should be false after reset.");
        assertFalse(data.gameStarted, "gameStarted should be false after reset.");
        assertFalse(data.gamePaused, "gamePaused should be false after reset.");
    }

    /**
     * tests that reset state sets the default frames correctly.
     */
    @Test
    void createResetState_setsDefaultFramesToFrameDown() {
        GameStateResetHelper.ResetStateData data =
                GameStateResetHelper.createResetState(Player.FRAME_DOWN);

        assertEquals(Player.FRAME_DOWN, data.currentFrame,
                "Player frame should reset to FRAME_DOWN.");
        assertEquals(Player.FRAME_DOWN, data.securityCurrentFrame,
                "Security frame should reset to FRAME_DOWN.");
    }

    /**
     * tests that reset state creates a security guard with default values.
     */
    @Test
    void createResetState_createsSecurityGuardWithDefaultValues() {
        GameStateResetHelper.ResetStateData data =
                GameStateResetHelper.createResetState(Player.FRAME_DOWN);

        assertNotNull(data.securityGuard, "Security guard should be created during reset.");
        assertEquals(500, data.securityGuard.getX(),
                "Security guard X should reset to default position.");
        assertEquals(350, data.securityGuard.getY(),
                "Security guard Y should reset to default position.");
    }

    /**
     * tests that reset state sets the default timing and enemy state values.
     */
    @Test
    void createResetState_setsDefaultTimingAndEnemyStateValues() {
        GameStateResetHelper.ResetStateData data =
                GameStateResetHelper.createResetState(Player.FRAME_DOWN);

        assertEquals(0, data.startTime, "startTime should reset to 0.");
        assertEquals(0, data.endTime, "endTime should reset to 0.");
        assertNull(data.enemyTarget, "Enemy target should reset to null.");
        assertEquals(0, data.enemyMoveCooldown, "Enemy move cooldown should reset to 0.");
    }
}
