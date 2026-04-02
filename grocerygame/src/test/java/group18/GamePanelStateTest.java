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
}