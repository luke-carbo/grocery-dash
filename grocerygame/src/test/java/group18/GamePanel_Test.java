package group18;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.Graphics;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GamePanel class.
 */
public class GamePanel_Test {

    private GamePanel panel;
    private JFrame testFrame;

    /**
     * our before each
     * creates game
     */
    @BeforeEach
    void setup_game() {
        panel = new GamePanel();
    }

    /**
     * our after each
     * deletes game
     */
    @AfterEach
    void delete_game() {
        if (testFrame != null) {
            testFrame.dispose();
            testFrame = null;
        }
    }

    // Key Press Simulation

    /**
     * Simulates a key press
     * @param keyCode the key code to simulate
     */
    private void pressKey(int keyCode) {
        KeyEvent event = new KeyEvent(
                panel,
                KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                keyCode,
                KeyEvent.CHAR_UNDEFINED
        );
        panel.dispatchEvent(event);
    }

    // Imaginary GUI

    /**
     * Creates an imaginary GUI for testing purposes
     * @return
     */
    private Graphics Imaginary_GUI() {
        testFrame = new JFrame();
        testFrame.setSize(800, 600);
        testFrame.add(panel);
        testFrame.setUndecorated(true);
        testFrame.setVisible(true);
        return panel.getGraphics();
    }

    // GamePanel Key Testing

    /**
     * tests that Enter starts the game.
     */
    @Test
    void test_start() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertTrue(panel.isGameStarted());
        assertFalse(panel.isGamePaused());
        assertFalse(panel.isGameWon());
        assertFalse(panel.isGameOver());
        g.dispose();
    }

    /**
     * similar to above but with a different key press
     */
    @Test
    void test_start_2() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_X);
        assertTrue(panel.isGameStarted());
        assertFalse(panel.isGamePaused());
        assertFalse(panel.isGameWon());
        assertFalse(panel.isGameOver());
        g.dispose();
    }

    /**
     * tests that R resets the game after it has started.
     */
    @Test
    void test_reset() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_R);
        assertFalse(panel.isGameStarted());
        assertFalse(panel.isGamePaused());
        assertFalse(panel.isGameWon());
        assertFalse(panel.isGameOver());
        g.dispose();
    }

    /**
     * similar to above but with a different key press
     */
    @Test
    void test_reset_2() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_ESCAPE);
        pressKey(KeyEvent.VK_R);
        assertFalse(panel.isGameStarted());
        assertFalse(panel.isGamePaused());
        assertFalse(panel.isGameWon());
        assertFalse(panel.isGameOver());
        g.dispose();
    }

    /**
     * tests that Escape pauses the game after it has started.
     */
    @Test
    void test_pause() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_ESCAPE);
        assertTrue(panel.isGameStarted());
        assertTrue(panel.isGamePaused());
        assertFalse(panel.isGameWon());
        assertFalse(panel.isGameOver());
        g.dispose();
    }

    /**
     * similar to above but with a different key press
     */
    @Test
    void test_pause_2() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ESCAPE);
        pressKey(KeyEvent.VK_R);
        pressKey(KeyEvent.VK_ESCAPE);
        assertFalse(panel.isGameStarted());
        assertFalse(panel.isGamePaused());
        assertFalse(panel.isGameWon());
        assertFalse(panel.isGameOver());
        g.dispose();
    }

    // Movement Input Testing

    /**
     * tests the movement inputs (Y)
     */
    @Test
    void test_move_Y() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_W);
            pressKey(KeyEvent.VK_S);
        });
        g.dispose();
    }

    /**
     * tests the movement inputs (X)
     */
    @Test
    void test_move_X() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_A);
            pressKey(KeyEvent.VK_D);
        });
        g.dispose();
    }

    /**
     * tests the movement inputs (D)
     */
    @Test
    void test_move_D_1() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_W);
            pressKey(KeyEvent.VK_D);
        });
        g.dispose();
    }

    /**
     * similar to above but with a different key press
     */
    @Test
    void test_move_D_2() {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_S);
            pressKey(KeyEvent.VK_A);
        });
        g.dispose();
    }

    /**
     * tests the movement inputs while the game is over
     */
    @Test
    void test_move_while_game_over() {
        pressKey(KeyEvent.VK_ENTER);
        panel.setGameOverForTest(true);
        assertDoesNotThrow(() -> pressKey(KeyEvent.VK_W));
    }
    /**
     * tests the movement inputs while the game is won
     */
    @Test
    void test_move_while_game_won() {
        pressKey(KeyEvent.VK_ENTER);
        panel.setGameWonForTest(true);
        assertDoesNotThrow(() -> pressKey(KeyEvent.VK_W));
    }

    /**
     * tests the player death
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    void test_player_death() throws InterruptedException {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_W);
            pressKey(KeyEvent.VK_D);
        });
        int maxWaitTime = 10000;
        int elapsedTime = 0;
        while(!panel.isGameOver() && elapsedTime < maxWaitTime) {
            Thread.sleep(200);
            elapsedTime += 200;
        }
        assertTrue(panel.isGameOver());
        g.dispose();
    }

    // Component Painting Testing (Just searching for exceptions)

    /**
     * tests the painting of the start screen
     */
    @Test
    void test_paint_start_screen() {
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    /**
     * tests the painting of the game won screen
     */
    @Test
    void test_paint_game_won_screen() {
        panel.startGameForTest();
        panel.setGameWonForTest(true);
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    /**
     * tests the painting of the game over screen
     */
    @Test
    void test_paint_game_over_screen() {
        panel.startGameForTest();
        panel.setGameOverForTest(true);
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    /**
     * tests the painting of the pause screen
     */
    @Test
    void test_paint_pause_screen() {
        panel.startGameForTest();
        panel.setGamePausedForTest(true);
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    /**
     * tests the painting of the pause screen
     */
    @Test
    void test_paint_highscore_screen() {
        Graphics g = Imaginary_GUI();
        pressKey(KeyEvent.VK_H);
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    /**
     * tests the painting of the items
     */
    @Test
    void test_paint_items() {
        panel.startGameForTest();
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }
}