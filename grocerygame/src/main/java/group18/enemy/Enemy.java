package group18.enemy;

import group18.Entity_Dynamic;

/**
 * The Enemy class represents a dynamic entity in the game that inherits
 * from the Entity_Dynamic class.
 */
public class Enemy extends Entity_Dynamic {

    public Enemy(int x, int y) {
        this.position_x = x;
        this.position_y = y;
    }

    /**
     * Handles collision detection and response for the enemy entity.
     */
    @Override
    public void collision() {
        // collision logic
    }

    /**
     * Updates the state of the enemy entity.
     */
    @Override
    public void update() {
        // enemy behaviour
    }
}