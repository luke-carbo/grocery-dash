package group18;

import group18.enemy.SecurityGuard;
import group18.mapCreation.Game_Map;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel {

    private int playerX = 165;
    private int playerY = 570;
    private final int playerSpeed = 3;
    private int playerHeight = 30;
    private int playerWidth = 30;

    private int score = 0;
    private long startTime;
    private boolean gameOver = false;

    private Game_Map game_map;
    private Set<Integer> keysHeld = new HashSet<>();

    private SecurityGuard securityGuard = new SecurityGuard(300, 300, 100);

    private int enemyDirection = 1;
    private int enemySpeed = 2;

    // Item position
    private int itemX = 500;
    private int itemY = 200;
    private boolean itemCollected = false;

    public GamePanel() {
        this.game_map = new Game_Map();
        this.startTime = System.currentTimeMillis();

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysHeld.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysHeld.remove(e.getKeyCode());
            }
        });

        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void update() {

        if (gameOver) return;

        int newX = playerX;
        int newY = playerY;

        if (keysHeld.contains(KeyEvent.VK_W)) {
            newY -= playerSpeed;
            if (!game_map.isSolid(playerX, newY)
                    && !game_map.isSolid(playerX + playerWidth - 1, newY)) {
                playerY = newY;
            }
        }

        if (keysHeld.contains(KeyEvent.VK_S)) {
            newY += playerSpeed;
            if (!game_map.isSolid(playerX, newY + playerHeight)
                    && !game_map.isSolid(playerX + playerWidth - 1, newY + playerHeight)) {
                playerY = newY;
            }
        }

        if (keysHeld.contains(KeyEvent.VK_A)) {
            newX -= playerSpeed;
            if (!game_map.isSolid(newX, playerY)
                    && !game_map.isSolid(newX, playerY + playerHeight - 1)) {
                playerX = newX;
            }
        }

        if (keysHeld.contains(KeyEvent.VK_D)) {
            newX += playerSpeed;
            if (!game_map.isSolid(newX + playerWidth, playerY)
                    && !game_map.isSolid(newX + playerWidth, playerY + playerHeight - 1)) {
                playerX = newX;
            }
        }

        moveEnemy();
        checkEnemyCollision();
        checkItemCollection();
    }

    private void moveEnemy() {

        int newX = securityGuard.getX() + enemySpeed * enemyDirection;

        if (game_map.isSolid(newX, securityGuard.getY()) ||
                game_map.isSolid(newX + 30, securityGuard.getY())) {

            enemyDirection *= -1;

        } else {

            securityGuard.setX(newX);

        }
    }

    private void checkEnemyCollision() {

        int enemyX = securityGuard.getX();
        int enemyY = securityGuard.getY();
        int enemySize = 30;

        boolean overlap =
                playerX < enemyX + enemySize &&
                        playerX + playerWidth > enemyX &&
                        playerY < enemyY + enemySize &&
                        playerY + playerHeight > enemyY;

        if (overlap) {
            gameOver = true;
        }
    }

    private void checkItemCollection() {

        if (itemCollected) return;

        boolean overlap =
                playerX < itemX + 20 &&
                        playerX + playerWidth > itemX &&
                        playerY < itemY + 20 &&
                        playerY + playerHeight > itemY;

        if (overlap) {
            itemCollected = true;
            score += 10;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        game_map.draw(g);

        long elapsedMillis = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedMillis / 1000;

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 40, 50);
        g.drawString("CMPT 276 Grocery Game", 320, 50);
        g.drawString("Time: " + elapsedSeconds + "s", 700, 50);

        // enemy
        g.setColor(Color.RED);
        g.fillRect(securityGuard.getX(), securityGuard.getY(), 30, 30);

        // item
        if (!itemCollected) {
            g.setColor(Color.YELLOW);
            g.fillRect(itemX, itemY, 20, 20);
        }

        // player
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 380, 300);
        }
    }
}