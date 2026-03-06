package group18.enemy;

import group18.Entity_Dynamic;

/**
 * The Enemy class represents a dynamic entity in the game that inherits
 * from the Entity_Dynamic class. This class handles behavior such as movement
 * and collisions, and maintains the position of the enemy.
 */
public class Enemy extends Entity_Dynamic {

    protected Enemy_Action currentAction;
    protected Enemy_Class enemyClass;

    /**
     * Enemy constructor, gets starting position and starting class and action
     * @param x starting position
     * @param y starting position
     * @param enemyClass starting class
     */
    public Enemy(int x, int y, Enemy_Class enemyClass) {
        this.position_x = x;
        this.position_y = y;
        this.enemyClass = enemyClass;
        this.currentAction = Enemy_Action.Idle;
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