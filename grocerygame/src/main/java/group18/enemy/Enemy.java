package group18.enemy;

import group18.Game_Entity;

/**
 * The Enemy class represents a dynamic entity in the game that inherits
 * from the Entity_Dynamic class. This class handles behavior such as movement
 * and collisions, and maintains the position of the enemy.
 */
public class Enemy extends Game_Entity {

    protected Enemy_Action currentAction;
    protected Enemy_Class enemyClass;

    /**
     * Enemy constructor, gets starting position and starting class and action
     * @param x starting position
     * @param y starting position
     * @param enemyClass starting class
     */
    public Enemy(int x, int y, Enemy_Class enemyClass) {
        setX(x);
        setY(y);
        this.enemyClass = enemyClass;
        this.currentAction = Enemy_Action.Idle;
    }

    public Enemy_Action getCurrentAction() {
        return currentAction;
    }
}