package group18;

import group18.mapCreation.Game_Map;

import java.util.Set;

/**
 * Represents a player character in the game.
 */
public class Player extends Entity_Dynamic{

    public static final int FRAME_LEFT = 0;
    public static final int FRAME_DOWN = 1;
    public static final int FRAME_UP = 2;
    public static final int FRAME_RIGHT = 3;

    private boolean alive;
    private int currentFrame = FRAME_DOWN;

    /**
     * Initializes a new Player instance with a specified starting position.
     * Most likely middle of map
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     */
    public Player(int x, int y) {
        this.position_x = x;
        this.position_y = y;
        this.alive = true;
    }

    @Override
    public void collision() {
        // Nothing (Cannot Collide with Self)
    }

    @Override
    public void update() {
        // default update is intentionally empty when no input/map context is provided.
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
    public void update(Set<Integer> keysHeld, int playerSpeed, Game_Map map, int playerWidth, int playerHeight) {
        int[] movement = PlayerMovementHelper.getMovementDelta(keysHeld, playerSpeed);
        int dX = movement[0];
        int dY = movement[1];

        currentFrame = PlayerMovementHelper.getFrame(
                keysHeld,
                currentFrame,
                FRAME_LEFT,
                FRAME_DOWN,
                FRAME_UP,
                FRAME_RIGHT
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

    private boolean canMoveTo(Game_Map map, int x, int y, int width, int height) {
        return !map.isSolid(x, y)
                && !map.isSolid(x + width - 1, y)
                && !map.isSolid(x, y + height - 1)
                && !map.isSolid(x + width - 1, y + height - 1);
    }
}
