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
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;

    private static final int playerWidth = 30;
    private static final int playerHeight = 30;
    private static final int playerSpeed = 3;

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

    private Image[] securityFrames;
    private int securityCurrentFrame = FRAME_DOWN;

    private int score = 0;
    private boolean score_saved = false;
    private long startTime;
    private long endTime = 0;

    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean gameStarted = false;
    private boolean gamePaused = false;

    private Game game = null;
    private final Game_Map game_map;
    private final Set<Integer> keysHeld = new HashSet<>();

    private SecurityGuard securityGuard;
    private final int enemySize = 30;

    private int[] itemX = {500, 650, 350};
    private int[] itemY = {200, 450, 350};
    private boolean[] itemCollected = {false, false, false};

    private List<Item_Bonus> Bonus_Items = new ArrayList<>();
    private List<Item_Penalty> Penalty_Items = new ArrayList<>();
    private Spawn_Bonus bonus_spawner;
    private Spawn_Penalty penalty_spawner;

    private int enemyMoveCooldown = 0;
    private final int enemyMoveDelay = 2;
    private final double enemySpeed = 3;

    private double enemyPosX;
    private double enemyPosY;
    private Point enemyTarget;


    public GamePanel() {
        this.game = game;
        this.bonus_spawner = new Spawn_Bonus(game);
        this.penalty_spawner = new Spawn_Penalty(game);
        this.game_map = new Game_Map();
        this.startTime = System.currentTimeMillis();
        loadPlayerFrames();
        loadSecurityFrames();
        resetGameState();

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
//        // Starting Bonus Items
//        for (int i = 0; i < 4; i++) {
//            Bonus_Items.add(bonus_spawner.spawnBonus());
//        }
//
//        // Starting Penalty Items
//        for (int i = 0; i < 2; i++) {
//            Penalty_Items.add(penalty_spawner.spawnPenalty());
//        }

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
        score_saved = false;
        startTime = 0;
        endTime = 0;

        gameOver = false;
        gameWon = false;
        gameStarted = false;
        gamePaused = false;

        currentFrame = FRAME_DOWN;
        securityCurrentFrame = FRAME_DOWN;
        keysHeld.clear();

        securityGuard = new SecurityGuard(500, 300, 100);
        enemyPosX = securityGuard.getX();
        enemyPosY = securityGuard.getY();
        enemyTarget = null;
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
            playerFrames = null; // fallback to rectangle
        }
    }

    private void loadSecurityFrames() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("tiles/security.png")) {
            if (stream == null) {
                throw new IllegalArgumentException("Missing resource: security.png");
            }

            BufferedImage sheet = ImageIO.read(stream);
            int frameW = sheet.getWidth() / 2;
            int frameH = sheet.getHeight() / 2;

            securityFrames = new Image[4];
            securityFrames[FRAME_LEFT] = sheet.getSubimage(0, 0, frameW, frameH);
            securityFrames[FRAME_DOWN] = sheet.getSubimage(frameW, 0, frameW, frameH);
            securityFrames[FRAME_UP] = sheet.getSubimage(frameW, frameH, frameW, frameH);
            securityFrames[FRAME_RIGHT] = sheet.getSubimage(0, frameH, frameW, frameH);
        } catch (Exception e) {
            System.err.println("Failed to load security frames: " + e.getMessage());
            securityFrames = null;
        }
    }


    private void update() {
        if (gameOver) {
            if(!score_saved) {
//                int high_score = score + (int) ((endTime -  startTime)/1000);
                int high_score = score;
                Score_Tracker.saveScore(high_score);
                score_saved = true;
                return;
            }
            return;
        };
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
            dY -= playerSpeed;
            currentFrame = FRAME_UP;
        }
        if (keysHeld.contains(KeyEvent.VK_S)) {
            dY += playerSpeed;
            currentFrame = FRAME_DOWN;
        }
        if (keysHeld.contains(KeyEvent.VK_D)) {
            dX += playerSpeed;
            currentFrame = FRAME_RIGHT;
        }
        if (keysHeld.contains(KeyEvent.VK_A)) {
            dX -= playerSpeed;
            currentFrame = FRAME_LEFT;
        }

        if (dX != 0 && dY != 0) {
            dX /= 1.5;
            dY /= 1.5;
        }

        if (dX == 0 && dY == 0) {
            currentFrame = FRAME_DOWN;
        }

        if (canMoveTo(playerX + dX, playerY, playerWidth, playerHeight)) {
            playerX += dX;
        }
        if (canMoveTo(playerX, playerY + dY, playerWidth, playerHeight)) {
            playerY += dY;
        }

        if (Item.bonus_count < Item.bonus_limit) {
            Bonus_Items.add(bonus_spawner.spawnBonus());
        }

        if (Item.penalty_count < Item.penalty_limit) {
            Penalty_Items.add(penalty_spawner.spawnPenalty());
        }

        moveEnemyTowardPlayer();
        checkEnemyCollision();
        checkItemCollection();
    }

    private void saveScoreOnce() {
        if (!score_saved) {
            int finalScore = score + (int) ((endTime - startTime) / 1000);
            Score_Tracker.saveScore(finalScore);
            score_saved = true;
        }
    }

    private boolean canMoveTo(int x, int y, int width, int height) {
        return !game_map.isSolid(x, y)
                && !game_map.isSolid(x + width - 1, y)
                && !game_map.isSolid(x, y + height - 1)
                && !game_map.isSolid(x + width - 1, y + height - 1);
    }

    private void moveEnemyTowardPlayer() {
        enemyPosX = securityGuard.getX();
        enemyPosY = securityGuard.getY();

        if (enemyTarget == null) {
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

            if (nextStep == null || !canMoveTo(nextStep.x, nextStep.y, enemySize, enemySize)) {
                return;
            }

            enemyTarget = nextStep;
        }

        double dx = enemyTarget.x - enemyPosX;
        double dy = enemyTarget.y - enemyPosY;
        double distance = Math.hypot(dx, dy);

        if (distance < 0.001) {
            enemyTarget = null;
            return;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            securityCurrentFrame = dx > 0 ? FRAME_RIGHT : FRAME_LEFT;
        } else {
            securityCurrentFrame = dy > 0 ? FRAME_DOWN : FRAME_UP;
        }

        double step = Math.min(enemySpeed, distance);
        int nextX = (int) Math.round(enemyPosX + (dx / distance) * step);
        int nextY = (int) Math.round(enemyPosY + (dy / distance) * step);

        if (canMoveTo(nextX, nextY, enemySize, enemySize)) {
            securityGuard.setX(nextX);
            securityGuard.setY(nextY);
        } else {
            enemyTarget = null;
            return;
        }

        if (Math.abs(securityGuard.getX() - enemyTarget.x) <= 1
                && Math.abs(securityGuard.getY() - enemyTarget.y) <= 1) {
            securityGuard.setX(enemyTarget.x);
            securityGuard.setY(enemyTarget.y);
            enemyTarget = null;
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
                score += 50;
            }
        }

        for (Item_Bonus item : Bonus_Items) {

            if (item.collected) {
                continue;
            }

            boolean overlap =
                    playerX < item.position_x + 20 &&
                            playerX + playerWidth > item.position_x &&
                            playerY < item.position_y + 20 &&
                            playerY + playerHeight > item.position_y;

            if (overlap) {
                item.collected = true;
                score += item.value;
                Item.bonus_count -= 1;
            }
        }

        for (Item_Penalty item : Penalty_Items) {

            if (item.collected) {
                continue;
            }

            boolean overlap =
                    playerX < item.position_x + 20 &&
                            playerX + playerWidth > item.position_x &&
                            playerY < item.position_y + 20 &&
                            playerY + playerHeight > item.position_y;

            if (overlap) {
                item.collected = true;
                score -= item.value;
                Item.penalty_count -= 1;
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

        if (securityFrames != null && securityFrames.length > 0) {
            g.drawImage(securityFrames[securityCurrentFrame], securityGuard.getX(), securityGuard.getY(), enemySize, enemySize, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(securityGuard.getX(), securityGuard.getY(), enemySize, enemySize);
        }

        g.setColor(Color.YELLOW);
        for (int i = 0; i < itemX.length; i++) {
            if (!itemCollected[i]) {
                g.fillRect(itemX[i], itemY[i], 20, 20);
            }
        }

        g.setColor(Color.ORANGE);
        for (Item_Bonus item : Bonus_Items) {
            if (!item.collected) {
                g.fillRect(item.position_x, item.position_y, 20, 20);
            }
        }

        g.setColor(Color.RED);
        for (Item_Penalty item : Penalty_Items) {
            if (!item.collected) {
                g.fillRect(item.position_x, item.position_y, 20, 20);
            }
        }

        if (playerFrames != null && playerFrames.length > 0) {
            g.drawImage(playerFrames[currentFrame], playerX, playerY, playerWidth, playerHeight, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(playerX, playerY, playerWidth, playerHeight);
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