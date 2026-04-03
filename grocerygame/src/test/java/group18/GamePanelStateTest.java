package group18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GamePanelState class.
 */
public class GamePanelStateTest {

    /**
     * tests that a new game panel starts with default game states
     */
    @Test
    void newGamePanel_startsWithDefaultGameStates() {
        GamePanel panel = new GamePanel();

        assertFalse(panel.isGameStarted(), "Game should not be started when the panel is first created.");
        assertFalse(panel.isGamePaused(), "Game should not be paused when the panel is first created.");
        assertFalse(panel.isGameOver(), "Game should not be over when the panel is first created.");
        assertFalse(panel.isGameWon(), "Game should not be won when the panel is first created.");
    }

    /**
     * tests that the game starts when the startGameForTest method is called
     */
    @Test
    void startGameForTest_setsGameStartedToTrue() {
        GamePanel panel = new GamePanel();

        panel.startGameForTest();

        assertTrue(panel.isGameStarted(), "Game should be started after startGameForTest is called.");
        assertFalse(panel.isGamePaused(), "Game should not be paused when it has just started.");
        assertFalse(panel.isGameOver(), "Game should not be over when it has just started.");
        assertFalse(panel.isGameWon(), "Game should not be won when it has just started.");
    }

    /**
     * tests that the game resets to default states when resetGameStateForTest is called
     */
    @Test
    void resetGameStateForTest_restoresDefaultGameStates() {
        GamePanel panel = new GamePanel();

        panel.startGameForTest();
        panel.resetGameStateForTest();

        assertFalse(panel.isGameStarted(), "Game should not be started after reset.");
        assertFalse(panel.isGamePaused(), "Game should not be paused after reset.");
        assertFalse(panel.isGameOver(), "Game should not be over after reset.");
        assertFalse(panel.isGameWon(), "Game should not be won after reset.");
    }

    /**
     * tests that the game is won when setGameWonForTest is called
     */
    @Test
    void setGameWonForTest_setsWonStateToTrue() {
        GamePanel panel = new GamePanel();

        panel.setGameWonForTest(true);

        assertTrue(panel.isGameWon(), "Game should be won after setGameWonForTest(true) is called.");
        assertFalse(panel.isGameOver(), "Game over should remain false when only win state is set.");
    }

    /**
     * tests that the game is over when setGameOverForTest is called
     */
    @Test
    void setGameOverForTest_setsGameOverStateToTrue() {
        GamePanel panel = new GamePanel();

        panel.setGameOverForTest(true);

        assertTrue(panel.isGameOver(), "Game should be over after setGameOverForTest(true) is called.");
        assertFalse(panel.isGameWon(), "Game won should remain false when only game over state is set.");
    }

    /**
     * tests that the game is paused when setGamePausedForTest is called
     */
    @Test
    void setGamePausedForTest_setsPausedStateToTrue() {
        GamePanel panel = new GamePanel();

        panel.setGamePausedForTest(true);

        assertTrue(panel.isGamePaused(), "Game should be paused after setGamePausedForTest(true) is called.");
        assertFalse(panel.isGameStarted(), "Game should remain not started unless explicitly started.");
        assertFalse(panel.isGameOver(), "Game over should remain false when only paused state is set.");
        assertFalse(panel.isGameWon(), "Game won should remain false when only paused state is set.");
    }
}
