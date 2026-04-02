package group18;

import group18.enemy.Enemy_Action;
import group18.enemy.SecurityGuard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SecurityGuard class.
 */
public class SecurityGuardUnitTest {

    private SecurityGuard guard;
    private Player player;

    @BeforeEach
    void setUp() {
        guard  = new SecurityGuard(120, 150, 300);
        player = new Player(60, 90);
    }

    /**
     * Test 1
     * Test to see if the player's initial position is correct.
     */
    @Test
    void guard_initialPosition_isCorrect() {
        assertEquals(120, guard.getX(),
                "Guard X should match the value passed to the constructor.");
        assertEquals(150, guard.getY(),
                "Guard Y should match the value passed to the constructor.");
    }

    /**
     * Test 2
     * After chasePlayer() is called, the guard's currentAction must be Chase.
     */
    @Test
    void guard_chasePlayer_setsActionToChase() {
        guard.chasePlayer(player);

        assertEquals(Enemy_Action.Chase, guard.getCurrentAction(),
                "Guard's currentAction should be Chase after chasePlayer() is called.");
    }

    /**
     * Test 3
     * Test to see if the guard's size is default.
     */
    @Test
    void guard_getEnemySize_returnsDefault() {
        assertEquals(30, guard.getEnemySize(),
                "Guard's enemy size should be 30 (DEFAULT_SIZE).");
    }

    /**
     * Test 4
     * Tests some guard initializations. Like ChaseState target, moveCooldown, frame.
     */
    @Test
    void guard_createChaseState_initializedCorrectly() {
        SecurityGuard.ChaseState state = guard.createChaseState(Player.FRAME_DOWN);

        assertNull(state.target,
                "ChaseState target should be null on creation.");
        assertEquals(0, state.moveCooldown,
                "ChaseState moveCooldown should be 0 on creation.");
        assertEquals(Player.FRAME_DOWN, state.frame,
                "ChaseState frame should match the initialFrame argument.");
    }

    /**
     * Test 5
     * Same idea as above but after resetChaseState rather than creation
     */
    @Test
    void guard_resetChaseState_setsAllValues() {
        SecurityGuard.ChaseState state = guard.createChaseState(Player.FRAME_DOWN);
        Point newTarget = new Point(200, 300);

        guard.resetChaseState(state, newTarget, 5, Player.FRAME_RIGHT);

        assertEquals(newTarget, state.target,
                "ChaseState target should match the value passed to resetChaseState().");
        assertEquals(5, state.moveCooldown,
                "ChaseState moveCooldown should match the value passed to resetChaseState().");
        assertEquals(Player.FRAME_RIGHT, state.frame,
                "ChaseState frame should match the value passed to resetChaseState().");
    }
}
