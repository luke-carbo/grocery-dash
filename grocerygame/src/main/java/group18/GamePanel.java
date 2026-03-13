package group18;

import group18.mapCreation.Game_Map;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {


    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 5;
    private Game_Map game_map;


    public GamePanel() {
        this.game_map = new Game_Map();

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_W) playerY -= playerSpeed;
                if (key == KeyEvent.VK_S) playerY += playerSpeed;
                if (key == KeyEvent.VK_A) playerX -= playerSpeed;
                if (key == KeyEvent.VK_D) playerX += playerSpeed;

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        game_map.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("CMPT 276 Grocery Game", 320, 50);

        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 40, 40);
    }
}