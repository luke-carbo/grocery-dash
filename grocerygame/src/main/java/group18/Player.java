package group18;

import group18.mapCreation.Map_Builder;

import java.util.Set;

/**
 * Represents a player character in the game.
 */
public class Player extends Game_Entity {

    public static final int FRAME_LEFT = 0;
    public static final int FRAME_DOWN = 1;
    public static final int FRAME_UP = 2;
    public static final int FRAME_RIGHT = 3;
    private int hitboxTolerance = 3;

    private boolean alive;
    private int currentFrame = FRAME_DOWN;

    /**
     * Initializes a new Player instance with a specified starting position.
     * Most likely middle of map
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     */
    public Player(int x, int y) {
        setX(x);
        setY(y);
        this.alive = true;
    }

    /**
     * Updates player movement and facing frame using current held keys and map collision.
     *
     * @param keysHeld currently held input keys
     * @param playerSpeed movement speed per update
     * @param map game map for collision checks
     * @param playerWidth hitbox width
     * @param playerHeight hitbox height
     */
    public void update(Set<Integer> keysHeld, int playerSpeed, Map_Builder map, int playerWidth, int playerHeight) {
        int[] movement = PlayerMovementHelper.getMovementDelta(keysHeld, playerSpeed);
        int dX = movement[0];
        int dY = movement[1];

        currentFrame = PlayerMovementHelper.getFrame(
                keysHeld
        );

        if (dX == 0 && dY == 0) {
            currentFrame = FRAME_DOWN;
        }

        if (canMoveTo(map, getX() + dX, getY(), playerWidth, playerHeight)) {
            setX(getX() + dX);
        }
        if (canMoveTo(map, getX(), getY() + dY, playerWidth, playerHeight)) {
            setY(getY() + dY);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setDead() {
        this.alive = false;
    }

    /** returns the current player sprite frame index. */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /** sets the current player sprite frame index. */
    public void setCurrentFrame(int frame) {
        this.currentFrame = frame;
    }

    private boolean canMoveTo(Map_Builder map, int x, int y, int width, int height) {
        return !map.isSolid(x+hitboxTolerance, y+hitboxTolerance)
                && !map.isSolid(x + width - 1-hitboxTolerance, y+hitboxTolerance)
                && !map.isSolid(x+hitboxTolerance, y + height - 1-hitboxTolerance)
                && !map.isSolid(x + width - 1-hitboxTolerance, y + height - 1-hitboxTolerance);
    }
}
