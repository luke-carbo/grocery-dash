package group18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameRulesHelper class.
 */
public class GameRulesHelperTest {

    /**
     * This tests the isPlayerAtExit method.
     */
    @Test
    void isPlayerAtExit_returnsTrue_whenPlayerOverlapsExit() {
        boolean result = GameRulesHelper.isPlayerAtExit(
                655, 0,
                30, 30,
                660, 0,
                10
        );

        assertTrue(result, "Player should be considered at the exit when overlapping it.");
    }

    /**
     * This tests the isPlayerAtExit method.
     */
    @Test
    void isPlayerAtExit_returnsFalse_whenPlayerDoesNotTouchExit() {
        boolean result = GameRulesHelper.isPlayerAtExit(
                100, 100,
                30, 30,
                660, 0,
                10
        );

        assertFalse(result, "Player should not be at the exit when far away from it.");
    }

    /**
     * This tests the hasWon method.
     */
    @Test
    void hasWon_returnsTrue_whenAllWinningConditionsAreMet() {
        boolean result = GameRulesHelper.hasWon(false, true, true);

        assertTrue(result, "Game should be won only when it was not already won, all main items are collected, and player is at exit.");
    }

    /**
     * This tests the hasWon method if the game is already won.
     */
    @Test
    void hasWon_returnsFalse_whenGameAlreadyWon() {
        boolean result = GameRulesHelper.hasWon(true, true, true);

        assertFalse(result, "Game should not be marked as newly won if it was already won.");
    }

    /**
     * This tests the hasWon method if the player has not collected all main items.
     */
    @Test
    void hasWon_returnsFalse_whenNotAllMainItemsCollected() {
        boolean result = GameRulesHelper.hasWon(false, false, true);

        assertFalse(result, "Game should not be won if all main items are not collected.");
    }

    /**
     * This tests the hasWon method if the player is not at the exit
     */
    @Test
    void hasWon_returnsFalse_whenPlayerNotAtExit() {
        boolean result = GameRulesHelper.hasWon(false, true, false);

        assertFalse(result, "Game should not be won if the player is not at the exit.");
    }
}