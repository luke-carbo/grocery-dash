package group18;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.Graphics;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class GamePanel_Testing {

    private GamePanel panel;
    private JFrame testFrame;

    @BeforeEach
    void setup_game() {
        panel = new GamePanel();
    }

    @AfterEach
    void delete_game() {
        if (testFrame != null) {
            testFrame.dispose();
            testFrame = null;
        }
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
        testFrame = new JFrame();
        testFrame.setSize(800, 600);
        testFrame.add(panel);
        testFrame.setUndecorated(true);
        testFrame.setVisible(true);
        return panel.getGraphics();
    }

    // GamePanel Key Testing
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

    @Test
    void test_player_death() throws InterruptedException {
        Graphics g = Imaginary_GUI();
        panel.paintComponent(g);
        pressKey(KeyEvent.VK_ENTER);
        assertDoesNotThrow(() -> {
            pressKey(KeyEvent.VK_W);
            pressKey(KeyEvent.VK_D);
        });
        Thread.sleep(10000);
        assertTrue(panel.isGameOver());
        g.dispose();
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
        panel.startGameForTest();
        Graphics g = Imaginary_GUI();
        assertDoesNotThrow(() -> panel.paintComponent(g));
        g.dispose();
    }
}