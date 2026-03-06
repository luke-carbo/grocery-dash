package group18;

/**
 * Represents a dynamic game entity with the ability to move within the game world.
 */
public abstract class Entity_Dynamic extends Game_Entity{
    /**
     * The speed at which this entity moves in the game world.
     */
    private int speed;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(speed > 0) this.speed = speed;
    }



}
