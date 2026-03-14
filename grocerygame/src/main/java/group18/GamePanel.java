package group18;

import group18.ai.Pathfinder;
import group18.enemy.SecurityGuard;
import group18.mapCreation.Game_Map;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel {

    private int playerX = 165;
    private int playerY = 570;
    private final int playerSpeed = 3;
    private final int playerHeight = 30;
    private final int playerWidth = 30;

    private int score = 0;
    private long startTime;
    private long endTime = 0;
    private boolean gameOver = false;

    private final Game_Map game_map;
    private final Set<Integer> keysHeld = new HashSet<>();

    private final SecurityGuard securityGuard = new SecurityGuard(500, 300, 100);
    private final int enemySize = 30;

    private int[] itemX = {500, 650, 350};
    private int[] itemY = {200, 450, 350};
    private boolean[] itemCollected = {false, false, false};

    private int enemyMoveCooldown = 0;
    private final int enemyMoveDelay = 14;

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
            if (canMoveTo(playerX, newY, playerWidth, playerHeight)) {
                playerY = newY;
            }
        }

        if (keysHeld.contains(KeyEvent.VK_S)) {
            newY += playerSpeed;
            if (canMoveTo(playerX, newY, playerWidth, playerHeight)) {
                playerY = newY;
            }
        }

        if (keysHeld.contains(KeyEvent.VK_A)) {
            newX -= playerSpeed;
            if (canMoveTo(newX, playerY, playerWidth, playerHeight)) {
                playerX = newX;
            }
        }

        if (keysHeld.contains(KeyEvent.VK_D)) {
            newX += playerSpeed;
            if (canMoveTo(newX, playerY, playerWidth, playerHeight)) {
                playerX = newX;
            }
        }

        moveEnemyTowardPlayer();
        checkEnemyCollision();
        checkItemCollection();
    }

    private boolean canMoveTo(int x, int y, int width, int height) {
        return !game_map.isSolid(x, y)
                && !game_map.isSolid(x + width - 1, y)
                && !game_map.isSolid(x, y + height - 1)
                && !game_map.isSolid(x + width - 1, y + height - 1);
    }

    private void moveEnemyTowardPlayer() {
        enemyMoveCooldown++;
        if (enemyMoveCooldown < enemyMoveDelay) {
            return;
        }
        enemyMoveCooldown = 0;

        Point nextStep = Pathfinder.getNextStep(
                game_map,
                securityGuard.getX(),
                securityGuard.getY(),
                playerX,
                playerY
        );

        if (nextStep != null && canMoveTo(nextStep.x, nextStep.y, enemySize, enemySize)) {
            securityGuard.setX(nextStep.x);
            securityGuard.setY(nextStep.y);
        }
    }

    private void checkEnemyCollision() {
        int enemyX = securityGuard.getX();
        int enemyY = securityGuard.getY();

        boolean overlap =
                playerX < enemyX + enemySize &&
                        playerX + playerWidth > enemyX &&
                        playerY < enemyY + enemySize &&
                        playerY + playerHeight > enemyY;

        if (overlap) {
            gameOver = true;
            endTime = System.currentTimeMillis();
        }
    }

    private void checkItemCollection() {
        for (int i = 0; i < itemX.length; i++) {
            if (itemCollected[i]) continue;

            boolean overlap =
                    playerX < itemX[i] + 20 &&
                            playerX + playerWidth > itemX[i] &&
                            playerY < itemY[i] + 20 &&
                            playerY + playerHeight > itemY[i];

            if (overlap) {
                itemCollected[i] = true;
                score += 10;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        game_map.draw(g);

        long currentTime = gameOver ? endTime : System.currentTimeMillis();
        long elapsedMillis = currentTime - startTime;
        long elapsedSeconds = elapsedMillis / 1000;

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 40, 50);
        g.drawString("CMPT 276 Grocery Game", 320, 50);
        g.drawString("Time: " + elapsedSeconds + "s", 700, 50);

        g.setColor(Color.RED);
        g.fillRect(securityGuard.getX(), securityGuard.getY(), enemySize, enemySize);

        g.setColor(Color.YELLOW);
        for (int i = 0; i < itemX.length; i++) {
            if (!itemCollected[i]) {
                g.fillRect(itemX[i], itemY[i], 20, 20);
            }
        }

        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, playerWidth, playerHeight);

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 380, 300);
        }
    }
}