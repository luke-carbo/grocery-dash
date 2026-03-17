package group18;

import group18.ai.Pathfinder;
import group18.enemy.SecurityGuard;
import group18.mapCreation.Game_Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;

    private static final int PLAYER_WIDTH = 30;
    private static final int PLAYER_HEIGHT = 30;
    private static final int PLAYER_SPEED = 3;

    private static final int ENEMY_SIZE = 30;
    private static final int ENEMY_MOVE_DELAY = 14;

    private static final int EXIT_X = 620;
    private static final int EXIT_Y = 70;
    private static final int EXIT_SIZE = 35;

    private int playerX;
    private int playerY;

    private Image[] playerFrames;
    private static final int FRAME_LEFT = 0;
    private static final int FRAME_DOWN = 1;
    private static final int FRAME_UP = 2;
    private static final int FRAME_RIGHT = 3;
    private int currentFrame = FRAME_DOWN;

    private int score;
    private boolean scoreSaved;
    private long startTime;
    private long endTime = 0;

    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean gameStarted = false;
    private boolean gamePaused = false;

    private final Game_Map game_map;
    private final Set<Integer> keysHeld = new HashSet<>();

    private SecurityGuard securityGuard;
    private int enemyMoveCooldown = 0;

    private int[] itemX;
    private int[] itemY;
    private boolean[] itemCollected;

    public GamePanel() {
        this.game_map = new Game_Map();
        loadPlayerFrames();
        resetGameState();

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (!gameStarted && key == KeyEvent.VK_ENTER) {
                    gameStarted = true;
                    startTime = System.currentTimeMillis();
                    repaint();
                    return;
                }

                if (key == KeyEvent.VK_R) {
                    resetGameState();
                    repaint();
                    return;
                }

                if (gameStarted && !gameOver && !gameWon && key == KeyEvent.VK_ESCAPE) {
                    gamePaused = !gamePaused;
                    repaint();
                    return;
                }

                if (!gameStarted || gamePaused || gameOver || gameWon) {
                    return;
                }

                keysHeld.add(key);
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

    private void resetGameState() {
        playerX = 165;
        playerY = 570;

        score = 0;
        scoreSaved = false;
        startTime = 0;
        endTime = 0;

        gameOver = false;
        gameWon = false;
        gameStarted = false;
        gamePaused = false;

        currentFrame = FRAME_DOWN;
        keysHeld.clear();

        securityGuard = new SecurityGuard(500, 300, 100);
        enemyMoveCooldown = 0;

        itemX = new int[]{500, 650, 350};
        itemY = new int[]{200, 450, 350};
        itemCollected = new boolean[]{false, false, false};
    }

    private void loadPlayerFrames() {
        String[] framePaths = {
                "tiles/tile_0023.png",
                "tiles/tile_0024.png",
                "tiles/tile_0025.png",
                "tiles/tile_0026.png"
        };

        playerFrames = new Image[framePaths.length];

        try {
            for (int i = 0; i < framePaths.length; i++) {
                InputStream stream = getClass().getClassLoader().getResourceAsStream(framePaths[i]);
                if (stream == null) {
                    throw new IllegalArgumentException("Missing resource: " + framePaths[i]);
                }
                playerFrames[i] = ImageIO.read(stream);
            }
        } catch (Exception e) {
            System.err.println("Failed to load player frames: " + e.getMessage());
            playerFrames = null;
        }
    }

    private void update() {
        if (!gameStarted || gamePaused) {
            return;
        }

        if (gameOver || gameWon) {
            saveScoreOnce();
            return;
        }

        int dX = 0;
        int dY = 0;

        if (keysHeld.contains(KeyEvent.VK_W)) {
            dY -= PLAYER_SPEED;
            currentFrame = FRAME_UP;
        }
        if (keysHeld.contains(KeyEvent.VK_S)) {
            dY += PLAYER_SPEED;
            currentFrame = FRAME_DOWN;
        }
        if (keysHeld.contains(KeyEvent.VK_D)) {
            dX += PLAYER_SPEED;
            currentFrame = FRAME_RIGHT;
        }
        if (keysHeld.contains(KeyEvent.VK_A)) {
            dX -= PLAYER_SPEED;
            currentFrame = FRAME_LEFT;
        }

        if (dX != 0 && dY != 0) {
            dX /= 1.5;
            dY /= 1.5;
        }

        if (dX == 0 && dY == 0) {
            currentFrame = FRAME_DOWN;
        }

        if (canMoveTo(playerX + dX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT)) {
            playerX += dX;
        }
        if (canMoveTo(playerX, playerY + dY, PLAYER_WIDTH, PLAYER_HEIGHT)) {
            playerY += dY;
        }

        moveEnemyTowardPlayer();
        checkEnemyCollision();
        checkItemCollection();
    }

    private void saveScoreOnce() {
        if (!scoreSaved) {
            int finalScore = score + (int) ((endTime - startTime) / 1000);
            Score_Tracker.saveScore(finalScore);
            scoreSaved = true;
        }
    }

    private boolean canMoveTo(int x, int y, int width, int height) {
        return !game_map.isSolid(x, y)
                && !game_map.isSolid(x + width - 1, y)
                && !game_map.isSolid(x, y + height - 1)
                && !game_map.isSolid(x + width - 1, y + height - 1);
    }

    private void moveEnemyTowardPlayer() {
        enemyMoveCooldown++;
        if (enemyMoveCooldown < ENEMY_MOVE_DELAY) {
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

        if (nextStep != null && canMoveTo(nextStep.x, nextStep.y, ENEMY_SIZE, ENEMY_SIZE)) {
            securityGuard.setX(nextStep.x);
            securityGuard.setY(nextStep.y);
        }
    }

    private void checkEnemyCollision() {
        int enemyX = securityGuard.getX();
        int enemyY = securityGuard.getY();

        boolean overlap =
                playerX < enemyX + ENEMY_SIZE &&
                        playerX + PLAYER_WIDTH > enemyX &&
                        playerY < enemyY + ENEMY_SIZE &&
                        playerY + PLAYER_HEIGHT > enemyY;

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
                            playerX + PLAYER_WIDTH > itemX[i] &&
                            playerY < itemY[i] + 20 &&
                            playerY + PLAYER_HEIGHT > itemY[i];

            if (overlap) {
                itemCollected[i] = true;
                score += 10;
            }
        }
    }

    private boolean allItemsCollected() {
        for (boolean collected : itemCollected) {
            if (!collected) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        game_map.draw(g);

        if (!gameStarted) {
            g.setColor(Color.WHITE);
            g.drawString("CMPT 276 Grocery Game", 320, 250);
            g.drawString("Press ENTER to Start", 335, 280);
            g.drawString("Controls: WASD to move", 330, 310);
            g.drawString("ESC = Pause, R = Restart", 325, 340);
            return;
        }

        long currentTime = (gameOver || gameWon) ? endTime : System.currentTimeMillis();
        long elapsedMillis = currentTime - startTime;
        long elapsedSeconds = elapsedMillis / 1000;

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 40, 50);
        g.drawString("CMPT 276 Grocery Game", 320, 50);
        g.drawString("Time: " + elapsedSeconds + "s", 700, 50);



        g.setColor(Color.RED);
        g.fillRect(securityGuard.getX(), securityGuard.getY(), ENEMY_SIZE, ENEMY_SIZE);

        g.setColor(Color.YELLOW);
        for (int i = 0; i < itemX.length; i++) {
            if (!itemCollected[i]) {
                g.fillRect(itemX[i], itemY[i], 20, 20);
            }
        }

        if (playerFrames != null && playerFrames.length > 0) {
            g.drawImage(playerFrames[currentFrame], playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
        }

        if (gameOver) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 380, 300);
            g.drawString("Press R to Restart", 360, 330);
        }

        if (gameWon) {
            g.setColor(Color.WHITE);
            g.drawString("YOU WIN", 390, 300);
            g.drawString("Press R to Restart", 360, 330);
        }

        if (gamePaused) {
            g.setColor(Color.WHITE);
            g.drawString("PAUSED", 390, 280);
            g.drawString("Press ESC to Resume", 350, 310);
            g.drawString("Press R to Restart", 355, 340);
        }

        if (!allItemsCollected()) {
            g.setColor(Color.WHITE);
            g.drawString("Collect all items, then go to EXIT", 290, 575);
        }
    }
}