package group18;

import group18.enemy.SecurityGuard;
import group18.mapCreation.Map_Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerEnemyIntegrationTest {

    private static final int PLAYER_WIDTH  = 30;
    private static final int PLAYER_HEIGHT = 30;
    private static final int PLAYER_SPEED  = 3;
    private static final int TILE_SIZE     = 30;

    private static final int OPEN_X = 2 * TILE_SIZE; // 60
    private static final int OPEN_Y = TILE_SIZE; // 30

    private Map_Builder map;
    private Player player;
    private SecurityGuard guard;
    private SecurityGuard.ChaseState chaseState;
    private SecurityGuard.FrameSet frames;

    //helper
    private int manhattanDistance(Player p, SecurityGuard g) {
        return Math.abs(p.getX() - g.getX()) + Math.abs(p.getY() - g.getY());
    }

    @BeforeEach
    void setUp() {
        map        = new Map_Builder();
        player     = new Player(OPEN_X, OPEN_Y);
        guard      = new SecurityGuard(OPEN_X + 4 * TILE_SIZE, OPEN_Y, 300);
        chaseState = guard.createChaseState(0);
        frames     = new SecurityGuard.FrameSet(Player.FRAME_LEFT, Player.FRAME_DOWN, Player.FRAME_UP, Player.FRAME_RIGHT);
    }

    /**
     * Test 1
     * SecurityGuard.collidesWithPlayer() reads both entity positions
     * and compares their hitboxes. When the guard is placed at the exact same pixel
     * as the player, the overlap check must return true.
     */
    @Test
    void guard_collidesWithPlayer_whenOnSameTile() {
        guard.setX(player.getX());
        guard.setY(player.getY());

        assertTrue(guard.collidesWithPlayer(player, PLAYER_WIDTH, PLAYER_HEIGHT),"Guard should collide with player when they share the same pixel position.");
    }

    /**
     * Test 2
     * Same collision check as above, but guard is several tiles away. Hitboxes must not overlap.
     */
    @Test
    void guard_doesNotCollide_whenFarAway() {
        player.setX(OPEN_X);
        player.setY(OPEN_Y);
        guard.setX(OPEN_X + 10 * TILE_SIZE);
        guard.setY(OPEN_Y);

        assertFalse(guard.collidesWithPlayer(player, PLAYER_WIDTH, PLAYER_HEIGHT), "Guard should not collide with player when they are far apart.");
    }

    /**
     * Test 3
     * All four components must cooperate for the guard to move one step closer to the player on the real map.
     */
    @Test
    void guard_movesCloserToPlayer_afterOneUpdate() {
        player.setX(OPEN_X);
        player.setY(OPEN_Y);
        guard.setX(OPEN_X + 4 * TILE_SIZE);
        guard.setY(OPEN_Y);

        int distanceBefore = manhattanDistance(player, guard);

        chaseState.setMoveCooldown(99);

        guard.update(
                map, player, chaseState,
                frames
        );

        int distanceAfter = manhattanDistance(player, guard);

        assertTrue(distanceAfter < distanceBefore,
                "Guard should be closer to the player after one update tick.");
    }

    /**
     * Test 4
     * must consult Map_Builder.isSolid() via canMoveTo() and never land on a wall.
     */
    @Test
    void guard_blockedBySolidTile_neverOccupiesWall() {
        player.setX(OPEN_X);
        player.setY(OPEN_Y);
        guard.setX(OPEN_X + 6 * TILE_SIZE);
        guard.setY(OPEN_Y);

        // Run 20 update ticks — enough for the guard to take several steps toward the player
        for (int i = 0; i < 20; i++) {
            chaseState.setMoveCooldown(99); // bypass move delay every tick
            guard.update(
                    map, player, chaseState,
                    frames
            );

            assertFalse(map.isSolid(guard.getX(), guard.getY()),
                    "Guard must not occupy a solid tile at tick " + i
                            + " (pos: " + guard.getX() + ", " + guard.getY() + ")");
        }
    }

    /**
     * Test 5
     * When the player tries to move into a wall tile, the map must block the move and the player's position must remain unchanged.
     */
    @Test
    void player_blockedBySolidTile_positionUnchanged() {
        player.setX(OPEN_X); // 60
        player.setY(OPEN_Y); // 30

        java.util.Set<Integer> keys = new java.util.HashSet<>();
        keys.add(java.awt.event.KeyEvent.VK_A); // move left toward wall

        int xBefore = player.getX();

        player.update(keys, PLAYER_SPEED, map, PLAYER_WIDTH, PLAYER_HEIGHT);

        assertEquals(xBefore, player.getX(),
                "Player X should not change when moving into a wall tile.");
    }


    /**
     * Test 6
     * The map confirms the destination is not solid, allowing the move.
     */
    @Test
    void player_movesOnOpenFloor_xIncreases() {
        player.setX(OPEN_X); // 60
        player.setY(OPEN_Y); // 30

        java.util.Set<Integer> keys = new java.util.HashSet<>();
        keys.add(java.awt.event.KeyEvent.VK_D); // move right into open floor

        int xBefore = player.getX();

        player.update(keys, PLAYER_SPEED, map, PLAYER_WIDTH, PLAYER_HEIGHT);

        assertEquals(xBefore + PLAYER_SPEED, player.getX(),
                "Player X should increase by playerSpeed when moving right on open floor.");
    }

    /**
     * Test 7
     * Positive case: guard at the same position must catch the player.
     */
    @Test
    void guard_catchesPlayer_whenAtExactSamePosition() {
        guard.setX(player.getX());
        guard.setY(player.getY());

        assertTrue(guard.catchesPlayer(player),
                "Guard should catch player when both share the exact same position.");
    }

    /**
     * Negative case: guard at a different position must not catch the player.
     */
    @Test
    void guard_doesNotCatchPlayer_whenAtDifferentPosition() {
        guard.setX(player.getX() + TILE_SIZE);
        guard.setY(player.getY());

        assertFalse(guard.catchesPlayer(player),
                "Guard should not catch player when positions differ.");
    }


    /**
     * Test 8
     * once the guard reaches its waypoint, the target must be cleared.
     */
    @Test
    void guard_chaseState_targetClearedAfterReachingWaypoint() {
        // Point the target at the guard's exact current position — it has already "arrived"
        Point TestPoint = new Point(guard.getX(), guard.getY());
        chaseState.setTarget(TestPoint);

        guard.moveTowardPlayer(
                map,
                player.getX(), player.getY(),
                chaseState,
                frames
        );

        assertNull(chaseState.getTarget(),
                "ChaseState target should be null after guard reaches its waypoint.");
    }
}