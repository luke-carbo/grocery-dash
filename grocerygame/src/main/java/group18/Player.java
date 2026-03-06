package group18;

/**
 * Represents a player character in the game.
 */
public class Player extends Entity_Dynamic{

    private boolean alive;

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
        // TODO: Read input from Keyboard and apply moves based on input.
        // TODO: Also check for collisions.
    }

    public boolean isAlive() {
        return alive;
    }

    public void setDead() {
        this.alive = false;
    }
}