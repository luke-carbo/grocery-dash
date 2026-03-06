package group18;

/**
 * Represents a dynamic game entity with the ability to move within the game world.
 */
public abstract class Entity_Dynamic extends Game_Entity{
    /**
     * The speed at which this entity moves in the game world.
     */
    private int speed;

    /**
     * Retrieves the x-coordinate of this entity.
     *
     * @return the current x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of this entity.
     * @return the current y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Updates the x-coordinate of this entity.
     * @param x the new x-coordinate to set for this entity.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Updates the y-coordinate of this entity.
     * @param y the new y-coordinate to set for this entity.
     */
    public void setY(int y) {
        this.y = y;
    }
}
