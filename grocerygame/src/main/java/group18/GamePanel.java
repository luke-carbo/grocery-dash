package group18;

import group18.enemy.SecurityGuard;
import group18.mapCreation.Map_Builder;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Security;
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

    private static final int PLAYER_SIZE = 30;
    private static final int PLAYER_SPEED = 3;

    private static final int EXIT_X = 660;
    private static final int EXIT_Y = 0;
    private static final int EXIT_SIZE = 10;

    private Player player;

    private Image[] playerFrames;

    private Image[] securityFrames;

    private int score = 0;
    private boolean score_saved = false;
    private long startTime;
    private long endTime = 0;

    private boolean gameOver = false;
    private boolean gameWon = false;
    private boolean gameStarted = false;
    private boolean gamePaused = false;

    private Game game = null;
    private final Map_Builder map_builder;
    private final Set<Integer> keysHeld = new HashSet<>();

    private SecurityGuard securityGuard;
    private SecurityGuard.ChaseState chaseState;

    private List<Item_Main> Main_Items = new ArrayList<>();
    private List<Item_Bonus> Bonus_Items = new ArrayList<>();
    private List<Item_Penalty> Penalty_Items = new ArrayList<>();
    private Spawn_Main main_spawner;
    private Spawn_Bonus bonus_spawner;
    private Spawn_Penalty penalty_spawner;

    /**
     * this panel runs the main gameplay loop and handles input, updates, and drawing.
     * it also initializes the map, player, enemy, and timers.
     */
    public GamePanel() {
        this.game = game;
        this.map_builder = new Map_Builder();
        this.main_spawner = new Spawn_Main(game, map_builder);
        this.bonus_spawner = new Spawn_Bonus(game, map_builder);
        this.penalty_spawner = new Spawn_Penalty(game, map_builder);
        this.startTime = System.currentTimeMillis();
        loadPlayerFrames();
        loadSecurityFrames();
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

    /**
     * this resets all gameplay values to their starting state.
     * it is used when the game starts and when the player restarts.
     */
    private void resetGameState() {
        GameStateResetHelper.ResetStateData data = GameStateResetHelper.createResetState(Player.FRAME_DOWN);

        startTime = data.startTime;
        endTime = data.endTime;

        gameOver = data.gameOver;
        gameWon = data.gameWon;
        gameStarted = data.gameStarted;
        gamePaused = data.gamePaused;

        keysHeld.clear();

        if (player == null) {
            player = new Player(data.playerX, data.playerY);
        } else {
            player.setX(data.playerX);
            player.setY(data.playerY);
        }
        player.setCurrentFrame(data.currentFrame);

        securityGuard = data.securityGuard;
        if (chaseState == null) {
            chaseState = securityGuard.createChaseState(Player.FRAME_DOWN);
        }
        securityGuard.resetChaseState(chaseState, data.enemyTarget, data.enemyMoveCooldown, data.securityCurrentFrame);

        score_saved = false;

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
            saveScoreOnce();
            return;
        }

        if (!gameStarted || gamePaused) {
            return;
        }

        if (gameWon) {
            saveScoreOnce();
            return;
        }

        player.update(keysHeld, PLAYER_SPEED, map_builder, PLAYER_SIZE, PLAYER_SIZE);

        Bonus_Items.removeIf(item -> item.collected);
        Penalty_Items.removeIf(item -> item.collected);

        if (Bonus_Items.size() < 4) {
            Bonus_Items.add(bonus_spawner.spawnBonus());
        }

        if (Penalty_Items.size() < 3) {
            Penalty_Items.add(penalty_spawner.spawnPenalty());
        }

        SecurityGuard.FrameSet frames = new SecurityGuard.FrameSet(
                Player.FRAME_LEFT, Player.FRAME_DOWN, Player.FRAME_UP, Player.FRAME_RIGHT
        );

        securityGuard.update(
                map_builder,
                player,
                chaseState,
                frames
        );
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
            Score_Tracker.saveScore(finalScore);
            score_saved = true;
        }
    }

    /**
     * this checks overlap between the player and enemy hitboxes.
     * it ends the game when they collide.
     */
    private void checkEnemyCollision() {
        if (securityGuard.collidesWithPlayer(player, PLAYER_SIZE, PLAYER_SIZE)) {
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
            score += item.collectIfTouched(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        }

        for (Item_Bonus item : Bonus_Items) {
            score += item.collectIfTouched(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        }

        for (Item_Penalty item : Penalty_Items) {
            score += item.collectIfTouched(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        }
    }

    /**
     * this marks the game as won when all items are collected and the player reaches the exit.
     * it also records the end time.
     */
    private void checkWinCondition() {
        boolean allMainItemsCollected = Item_Main.areAllCollected(Main_Items);
        boolean playerAtExit = GameRulesHelper.isPlayerAtExit(
                player.getX(),
                player.getY(),
                PLAYER_SIZE,
                PLAYER_SIZE,
                EXIT_X,
                EXIT_Y,
                EXIT_SIZE
        );
        if (GameRulesHelper.hasWon(gameWon, allMainItemsCollected, playerAtExit)) {
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

        map_builder.draw(g);

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
            g.drawImage(
                    securityFrames[chaseState.getFrame()],
                    securityGuard.getX(),
                    securityGuard.getY(),
                    securityGuard.getEnemySize(),
                    securityGuard.getEnemySize(),
                    null
            );
        } else {
            g.setColor(Color.RED);
            g.fillRect(
                    securityGuard.getX(),
                    securityGuard.getY(),
                    securityGuard.getEnemySize(),
                    securityGuard.getEnemySize()
            );
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
            g.drawImage(playerFrames[player.getCurrentFrame()], player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
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

        if (!Item_Main.areAllCollected(Main_Items)) {
            g.setColor(Color.WHITE);
            g.drawString("Collect all items, then go to EXIT", 290, 575);
        }
    }
}