package group18;

import group18.mapCreation.Game_Map;

import javax.swing.JFrame;
import java.awt.*;

/**
 * Main game class for Grocery Game.
 */
public class Game extends JFrame {
    public Game() {
        setTitle("CMPT276 Grocery Game");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);

        setVisible(true);
        pack();
    }
}