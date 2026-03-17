package group18;

import group18.ai.Pathfinder;
import group18.enemy.SecurityGuard;
import group18.mapCreation.Game_Map;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * this panel controls the playable game scene.
 * it handles input, updates state, and renders each frame.
 */
public class GamePanel extends JPanel {

    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;

    private static final int playerWidth = 30;
    private static final int playerHeight = 30;
    private static final int playerSpeed = 3;

    private static final int EXIT_X = 660;
    private static final int EXIT_Y = 0;
    private static final int EXIT_SIZE = 10;

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

    private List<Item_Main> Main_Items = new ArrayList<>();
    private List<Item_Bonus> Bonus_Items = new ArrayList<>();
    private List<Item_Penalty> Penalty_Items = new ArrayList<>();
    private Spawn_Main main_spawner;
    private Spawn_Bonus bonus_spawner;
    private Spawn_Penalty penalty_spawner;

    private int enemyMoveCooldown = 0;
    private final int enemyMoveDelay = 2;
    private final double enemySpeed = 3;

    private double enemyPosX;
    private double enemyPosY;
    private Point enemyTarget;

    /**
     * this panel runs the main gameplay loop and handles input, updates, and drawing.
     * it also initializes the map, player, enemy, and timers.
     */
    public GamePanel() {
        this.game = game;
        this.game_map = new Game_Map();
        this.main_spawner = new Spawn_Main(game, game_map);
        this.bonus_spawner = new Spawn_Bonus(game, game_map);
        this.penalty_spawner = new Spawn_Penalty(game, game_map);
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

    /**
     * this resets all gameplay values to their starting state.
     * it is used when the game starts and when the player restarts.
     */
    private void resetGameState() {
        GameStateResetHelper.ResetStateData data = GameStateResetHelper.createResetState(FRAME_DOWN);

        playerX = data.playerX;
        playerY = data.playerY;

        score = data.score;
        score_saved = data.score_saved;
        startTime = data.startTime;
        endTime = data.endTime;

        gameOver = data.gameOver;
        gameWon = data.gameWon;
        gameStarted = data.gameStarted;
        gamePaused = data.gamePaused;

        currentFrame = data.currentFrame;
        securityCurrentFrame = data.securityCurrentFrame;
        keysHeld.clear();

        securityGuard = data.securityGuard;
        enemyPosX = data.enemyPosX;
        enemyPosY = data.enemyPosY;
        enemyTarget = data.enemyTarget;
        enemyMoveCooldown = data.enemyMoveCooldown;

        Main_Items.clear();

        // Starting Main Items
        for (int i = 0; i < 3; i++) {
            Main_Items.add(main_spawner.spawnMain());
        }

    }

    /**
     * this loads the player sprite frames from the sprite loader.
     * it prepares images used for player direction animation.
     */
    private void loadPlayerFrames() {
        playerFrames = SpriteLoader.loadCharacterFrames();
    }

    /**
     * this loads the security guard sprite frames.
     * it prepares images used for enemy direction animation.
     */
    private void loadSecurityFrames() {
        securityFrames = SpriteLoader.loadSecurityFrames();
    }

    /**
     * this updates one game tick for movement, collisions, and win or lose checks.
     * it returns early when the game is not active.
     */
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
        }

        if (!gameStarted || gamePaused) {
            return;
        }

        if (gameOver || gameWon) {
            saveScoreOnce();
            return;
        }

        int[] movement = PlayerMovementHelper.getMovementDelta(keysHeld, playerSpeed);
        int dX = movement[0];
        int dY = movement[1];

        currentFrame = PlayerMovementHelper.getFrame(keysHeld, currentFrame, FRAME_LEFT, FRAME_DOWN, FRAME_UP, FRAME_RIGHT);

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
        checkWinCondition();
    }

    /**
     * this computes the final score one time when the game ends.
     * it prevents duplicate score handling.
     */
    private void saveScoreOnce() {
        if (!score_saved) {
            int finalScore = score;
            score_saved = true;
        }
    }

    /**
     * this checks whether all four corners of a rectangle are on walkable tiles.
     * it returns true when movement to the target area is allowed.
     *
     * @param x the rectangle x position
     * @param y the rectangle y position
     * @param width the rectangle width
     * @param height the rectangle height
     * @return true if the rectangle does not collide with solid tiles
     */
    private boolean canMoveTo(int x, int y, int width, int height) {
        return !game_map.isSolid(x, y)
                && !game_map.isSolid(x + width - 1, y)
                && !game_map.isSolid(x, y + height - 1)
                && !game_map.isSolid(x + width - 1, y + height - 1);
    }

    /**
     * this moves the enemy toward the player using pathfinding and smooth stepping.
     * it updates the enemy facing frame based on movement direction.
     */
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

    /**
     * this checks overlap between the player and enemy hitboxes.
     * it ends the game when they collide.
     */
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

    /**
     * this checks overlap between the player and collectible items.
     * it updates score and item counters when items are picked up.
     */
    private void checkItemCollection() {

        for (Item_Main item : Main_Items) {

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

    /**
     * this checks if every required base item has been collected.
     * it returns true only when none are left.
     *
     * @return true if all base items are collected
     */
    private boolean allItemsCollected() {
        for (Item_Main item : Main_Items) {
            if (!item.collected) {
                return false;
            }
        }
        return true;
    }

    /**
     * this checks whether the player is overlapping the exit area.
     * it is used for win condition validation.
     *
     * @return true if the player touches the exit
     */
    private boolean isPlayerAtExit() {
        return playerX < EXIT_X + EXIT_SIZE &&
                playerX + playerWidth > EXIT_X &&
                playerY < EXIT_Y + EXIT_SIZE &&
                playerY + playerHeight > EXIT_Y;
    }

    /**
     * this marks the game as won when all items are collected and the player reaches the exit.
     * it also records the end time.
     */
    private void checkWinCondition() {
        if (!gameWon && allItemsCollected() && isPlayerAtExit()) {
            gameWon = true;
            endTime = System.currentTimeMillis();
        }
    }

    /**
     * this draws the map, entities, ui text, and state overlays each frame.
     * it also draws start, pause, win, and game over screens.
     *
     * @param g the graphics context used for rendering
     */
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
        for (Item_Main item : Main_Items) {
            if (!item.collected) {
                g.fillRect(item.position_x, item.position_y, 20, 20);
            }
        }

        g.setColor(Color.GREEN);
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
