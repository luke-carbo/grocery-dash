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

    private Game_Painting Painter;

    private static final int PANEL_WIDTH = 810;
    private static final int PANEL_HEIGHT = 660;

    private static final int PLAYER_SIZE = 30;
    private static final int PLAYER_SPEED = 3;

    private static final int EXIT_X = 660;
    private static final int EXIT_Y = 60;
    private static final int EXIT_SIZE = 10;

    private Player player;

    private Image[] playerFrames;

    private Image[] securityFrames;

    private int score = 0;
    private boolean score_saved = false;
    private long startTime;
    private long endTime = 0;
    private List<Integer> Highscores;
    private boolean showHighscores = false;

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
        this.map_builder = new Map_Builder();
        initializeDependencies();
        loadAssets();
        initializePainterAndState();
        configurePanel();
        setupInputHandling();
        startGameLoop();
    }

    private void initializeDependencies() {
        this.game = game;
        this.main_spawner = new Spawn_Main(game, map_builder);
        this.bonus_spawner = new Spawn_Bonus(game, map_builder);
        this.penalty_spawner = new Spawn_Penalty(game, map_builder);
        this.startTime = System.currentTimeMillis();
    }

    private void loadAssets() {
        loadPlayerFrames();
        loadSecurityFrames();
    }

    private void initializePainterAndState() {
        Painter = new Game_Painting(map_builder, playerFrames, securityFrames);
        resetGameState();
    }

    private void configurePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    private void setupInputHandling() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (!gameStarted && key == KeyEvent.VK_H) {
                    Highscores = Score_Tracker.loadScore();
                    showHighscores = true;
                    repaint();
                    return;
                }

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
    }

    private void startGameLoop() {
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

        Highscores = data.Highscores;
        showHighscores = false;

        score = data.score;
        score_saved = false;

        Main_Items.clear();

        // Starting Main Items
        for (int i = 0; i < Item.main_limit; i++) {
            Main_Items.add(main_spawner.spawnMain());
        }

    }


    boolean isGameStarted() {
        return gameStarted;
    }

    boolean isGamePaused() {
        return gamePaused;
    }

    boolean isGameOver() {
        return gameOver;
    }

    boolean isGameWon() {
        return gameWon;
    }

    void startGameForTest() {
        gameStarted = true;
        startTime = System.currentTimeMillis();
    }

    void resetGameStateForTest() {
        resetGameState();
    }

    void setGameWonForTest(boolean value) {
        gameWon = value;
    }

    void setGameOverForTest(boolean value) {
        gameOver = value;
    }

    void setGamePausedForTest(boolean value) {
        gamePaused = value;
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

        if (Bonus_Items.size() < Item.bonus_limit) {
            Bonus_Items.add(bonus_spawner.spawnBonus());
        }

        if (Penalty_Items.size() < Item.penalty_limit) {
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
        Painter.Paint(
                g,
                gameStarted, gameOver, gameWon, gamePaused,
                score, startTime, endTime,
                player, PLAYER_SIZE,
                securityGuard, chaseState,
                Main_Items, Bonus_Items, Penalty_Items,
                Highscores, showHighscores
        );
    }
}
