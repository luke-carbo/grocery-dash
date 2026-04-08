package group18;

import group18.enemy.SecurityGuard;
import group18.mapCreation.Map_Builder;

import javax.swing.*;
import java.awt.*;
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

    private Game_Painting Painter;

    private static final int PANEL_WIDTH = 990;
    private static final int PANEL_HEIGHT = 780;
    private static final int GAME_LOOP_DELAY_MS = 16;

    private static final int PLAYER_SIZE = 30;
    private static final int PLAYER_SPEED = 3;

    private static final int EXIT_X = 840;
    private static final int EXIT_Y = 60;
    private static final int EXIT_SIZE = 10;

    private Player player;

    private Image[] playerFrames;

    private Image[] securityFrames;

    private Image bonusSprite;
    private Image penaltySprite;
    private Image mainSprite;

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

    private long player_stunTime;
    private boolean player_stunned = false;
    private long guard_stunTime;
    private boolean guard_stunned = false;

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
        loadBonusSprite();
        loadPenaltySprite();
        loadMainSprite();
    }

    private void initializePainterAndState() {
        Painter = new Game_Painting(map_builder, playerFrames, securityFrames, bonusSprite, penaltySprite, mainSprite);
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
                handleKeyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysHeld.remove(e.getKeyCode());
            }
        });
    }

    private void handleKeyPressed(int key) {
        if (handleMenuKey(key)) {
            return;
        }

        if (handleControlKey(key)) {
            return;
        }

        if (!isGameplayInputAllowed()) {
            return;
        }

        keysHeld.add(key);
    }

    private boolean handleMenuKey(int key) {
        if (!gameStarted && key == KeyEvent.VK_H) {
            Highscores = Score_Tracker.loadScore();
            showHighscores = true;
            repaint();
            return true;
        }

        if (!gameStarted && key == KeyEvent.VK_ENTER) {
            gameStarted = true;
            startTime = System.currentTimeMillis();
            repaint();
            return true;
        }

        return false;
    }

    private boolean handleControlKey(int key) {
        if (key == KeyEvent.VK_R) {
            resetGameState();
            repaint();
            return true;
        }

        if (gameStarted && !gameOver && !gameWon && key == KeyEvent.VK_ESCAPE) {
            gamePaused = !gamePaused;
            repaint();
            return true;
        }

        return false;
    }

    private boolean isGameplayInputAllowed() {
        return gameStarted && !gamePaused && !gameOver && !gameWon;
    }

    private void startGameLoop() {
        Timer timer = new Timer(GAME_LOOP_DELAY_MS, e -> {
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

        player_stunned = false;
        guard_stunned = false;

        Main_Items.clear();

        // Starting Main Items
//        for (int i = 0; i < Item.main_limit; i++) {
//            Main_Items.add(main_spawner.spawnMain());
//        }

        Set<Point> occupied = new HashSet<>();
        for (int i = 0; i < Item.main_limit; i++) {
            Item_Main spawned = main_spawner.spawnMain(occupied);
            occupied.add(new Point(spawned.getX(), spawned.getY()));
            Main_Items.add(spawned);
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
     * this loads the bonus item sprite.
     */
    private void loadBonusSprite() {
        bonusSprite = SpriteLoader.loadBonusSprite();
    }

    /**
     * this loads the penalty item sprite.
     */
    private void loadPenaltySprite() {
        penaltySprite = SpriteLoader.loadPenaltySprite();
    }

    /**
     * this loads the main item sprite.
     */
    private void loadMainSprite() {
        mainSprite = SpriteLoader.loadMainSprite();
    }



    /**
     * this updates one game tick for movement, collisions, and win or lose checks.
     * it returns early when the game is not active.
     */
    private void update() {
        if (gameWon) {
            saveScoreOnce();
            return;
        }

        if (gameOver) {
            return;
        }

        if (!gameStarted || gamePaused) {
            return;
        }

        if (player_stunned && System.currentTimeMillis() >= player_stunTime) {
            player_stunned = false;
        }

        if (!player_stunned) {
            player.update(keysHeld, PLAYER_SPEED, map_builder, PLAYER_SIZE, PLAYER_SIZE);
        }

        Bonus_Items.removeIf(item -> item.collected);
        Penalty_Items.removeIf(item -> item.collected);

//        if (Bonus_Items.size() < Item.bonus_limit) {
//            Bonus_Items.add(bonus_spawner.spawnBonus());
//        }

        if (Bonus_Items.size() < Item.bonus_limit) {
            Set<Point> occupied = SpawnHelper.occupiedPoints(Main_Items);
            Bonus_Items.stream().filter(i -> !i.collected)
                    .forEach(i -> occupied.add(new Point(i.getX(), i.getY())));
            Penalty_Items.stream().filter(i -> !i.collected)
                    .forEach(i -> occupied.add(new Point(i.getX(), i.getY())));
            Bonus_Items.add(bonus_spawner.spawnBonus(occupied));
        }
//
//        if (Penalty_Items.size() < Item.penalty_limit) {
//            Penalty_Items.add(penalty_spawner.spawnPenalty());
//        }

        if (Penalty_Items.size() < Item.penalty_limit) {
            Set<Point> occupied = SpawnHelper.occupiedPoints(Main_Items);
            Bonus_Items.stream().filter(i -> !i.collected)
                    .forEach(i -> occupied.add(new Point(i.getX(), i.getY())));
            Penalty_Items.stream().filter(i -> !i.collected)
                    .forEach(i -> occupied.add(new Point(i.getX(), i.getY())));
            Penalty_Items.add(penalty_spawner.spawnPenalty(occupied));
        }

        if (guard_stunned && System.currentTimeMillis() >= guard_stunTime) {
            guard_stunned = false;
        }

        if (!guard_stunned) {
        SecurityGuard.FrameSet frames = new SecurityGuard.FrameSet(
                Player.FRAME_LEFT, Player.FRAME_DOWN, Player.FRAME_UP, Player.FRAME_RIGHT
        );

            securityGuard.update(
                    map_builder,
                    player,
                    chaseState,
                    frames
            );
        }

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
        int old_value = score;

        for (Item_Main item : Main_Items) {
            score += item.collectIfTouched(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
            if (old_value < score) {
                guard_stunned = true;
                guard_stunTime = System.currentTimeMillis() + 1000;
            }
        }

        for (Item_Bonus item : Bonus_Items) {
            score += item.collectIfTouched(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
        }

        for (Item_Penalty item : Penalty_Items) {
            score += item.collectIfTouched(player.getX(), player.getY(), PLAYER_SIZE, PLAYER_SIZE);
            if (old_value > score) {
                player_stunned = true;
                player_stunTime = System.currentTimeMillis() + 500;
            }
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
