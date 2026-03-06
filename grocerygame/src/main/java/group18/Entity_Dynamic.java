package group18;

/**
 * Represents a dynamic game entity with the ability to move within the game world.
 */
public abstract class Entity_Dynamic extends Game_Entity {

    /**
     * The speed at which this entity moves in the game world.
     */
    private int speed;

    /**
     * Speed getter
     * @return The current speed of this entity.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Speed setter
     * @param speed The new speed for this entity.
     */
    public void setSpeed(int speed) {
        if (speed > 0) {
            this.speed = speed;
        }
    }
}