package group18;

import group18.enemy.SecurityGuard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanel_Testing {

    private GamePanel panel;

    @BeforeEach
    void setup_game() {
        panel = new GamePanel();
    }

    // Key Press Simulation
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
    private Graphics Imaginary_GUI() {
        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        return img.getGraphics();
    }

    // GamePanel Key Testing
    @Test
    void test_reset() {
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_R);
        assertFalse(panel.isGameStarted());
    }

    @Test
    void test_reset_2() {
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_ESCAPE); // pause
        pressKey(KeyEvent.VK_R);      // reset
        assertFalse(panel.isGamePaused());
    }

    // Movement Input Testing
    @Test
    void test_move_before_start() {
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_W);
            pressKey(KeyEvent.VK_A);
            pressKey(KeyEvent.VK_S);
            pressKey(KeyEvent.VK_D);
        });
    }

    @Test
    void test_move_while_paused() {
        pressKey(KeyEvent.VK_ENTER);
        pressKey(KeyEvent.VK_ESCAPE);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_W);
            pressKey(KeyEvent.VK_A);
            pressKey(KeyEvent.VK_S);
            pressKey(KeyEvent.VK_D);
        });
    }

    @Test
    void test_move_while_game_over() {
        pressKey(KeyEvent.VK_ENTER);
        panel.setGameOverForTest(true);
        assertDoesNotThrow(() -> pressKey(KeyEvent.VK_W));
    }

    @Test
    void test_move_while_game_won() {
        pressKey(KeyEvent.VK_ENTER);
        panel.setGameWonForTest(true);
        assertDoesNotThrow(() -> pressKey(KeyEvent.VK_W));
    }

    // Component Painting Testing (Just searching for exceptions)
    @Test
    void test_paint_start_screen() {
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    @Test
    void test_paint_game_won_screen() {
        panel.startGameForTest();
        panel.setGameWonForTest(true);
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    @Test
    void test_paint_game_over_screen() {
        panel.startGameForTest();
        panel.setGameOverForTest(true);
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    @Test
    void test_paint_pause_screen() {
        panel.startGameForTest();
        panel.setGamePausedForTest(true);
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }

    @Test
    void test_paint_items() {
        Graphics g = Imaginary_GUI();
        panel.startGameForTest();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }
}