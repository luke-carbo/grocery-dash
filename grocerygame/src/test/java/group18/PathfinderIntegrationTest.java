package group18;

import group18.ai.Pathfinder;
import group18.mapCreation.Map_Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Pathfinder class.
 */
public class PathfinderIntegrationTest {

    private static final int TILE_SIZE = 30;

    private static final int START_X  = 2 * TILE_SIZE; // 60
    private static final int START_Y  = 3 * TILE_SIZE; // 90
    private static final int TARGET_X = 6 * TILE_SIZE; // 180
    private static final int TARGET_Y = 3 * TILE_SIZE; // 90

    private Map_Builder map;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        // Image loading fails silently in headless env — isSolidTile() works fine without images
        map = new Map_Builder();
    }

    /**
     * Test 1
     * When start cord and target cord r both on the floor, it should return a valid next step and not NULL.
     */
    @Test
    void pathfinder_returnsNonNull_whenPathExists() {
        Point nextStep = Pathfinder.getNextStep(map, START_X, START_Y, TARGET_X, TARGET_Y);

        assertNotNull(nextStep,
                "Pathfinder should return a valid next step when a clear path exists.");
    }

    /**
     * Test 2
     * the next step that pathfinder provides needs to be next to start position.
     */
    @Test
    void pathfinder_nextStep_isAdjacentToStart() {
        Point nextStep = Pathfinder.getNextStep(map, START_X, START_Y, TARGET_X, TARGET_Y);

        assertNotNull(nextStep, "Next step should not be null.");

        int dx = Math.abs(nextStep.x - START_X);
        int dy = Math.abs(nextStep.y - START_Y);

        boolean isOneStepAway = (dx == TILE_SIZE && dy == 0) || (dx == 0 && dy == TILE_SIZE);

        assertTrue(isOneStepAway,
                "Next step should be exactly one tile away from start. "
                        + "Got dx=" + dx + ", dy=" + dy);
    }
}
