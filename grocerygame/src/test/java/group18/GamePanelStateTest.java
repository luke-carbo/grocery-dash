package group18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanelStateTest {

    @Test
    void newGamePanel_startsWithDefaultGameStates() {
        GamePanel panel = new GamePanel();

        assertFalse(panel.isGameStarted(), "Game should not be started when the panel is first created.");
        assertFalse(panel.isGamePaused(), "Game should not be paused when the panel is first created.");
        assertFalse(panel.isGameOver(), "Game should not be over when the panel is first created.");
        assertFalse(panel.isGameWon(), "Game should not be won when the panel is first created.");
    }

    @Test
    void startGameForTest_setsGameStartedToTrue() {
        GamePanel panel = new GamePanel();

        panel.startGameForTest();

        assertTrue(panel.isGameStarted(), "Game should be started after startGameForTest is called.");
        assertFalse(panel.isGamePaused(), "Game should not be paused when it has just started.");
        assertFalse(panel.isGameOver(), "Game should not be over when it has just started.");
        assertFalse(panel.isGameWon(), "Game should not be won when it has just started.");
    }

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
}
