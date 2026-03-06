package group18;

/**
 * Abstract master entity class with constructors and getters for standard entity values.
 */
public abstract class Game_Entity {
    /**
     * The entity's X-Coordinate on the Map.
     */
    protected int position_x;

    /**
     * The entity's Y-Coordinate on the Map.
     */
    protected int position_y;

    /**
     * Abstract declaration for different collision types and handling
     */
    public abstract void collision();

    /**
     * Abstract declaration for different update types and handling
     */
    public abstract void update();

    /**
     * Retrieves the X-Coordinate of this entity.
     *
     * @return the current X-Coordinate.
     */
    public int getX() {
        return position_x;
    }

    /**
     * Retrieves the Y-Coordinate of this entity.
     * @return the current Y-Coordinate.
     */
    public int getY() {
        return position_y;
    }

    /**
     * Updates the X-Coordinate of this entity.
     * @param x the new X-Coordinate to set for this entity.
     */
    public void setX(int x) {
        this.position_x = x;
    }

    /**
     * Updates the Y-Coordinate of this entity.
     * @param y the new Y-Coordinate to set for this entity.
     */
    public void setY(int y) {
        this.position_y = y;
    }
}
