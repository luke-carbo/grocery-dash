package group18;

/**
 * Master class for stationary items
 */
public abstract class
Item extends Game_Entity {
    /**
     * The entity's score value. Can be negative (Punishments)
     */
    public int value;

    public static int main_limit = 3;
    public static int bonus_limit = 4;
    public static int penalty_limit = 3;

    /**
     * Enum determining Item Class
     */
    Item_Class type;

    public boolean collected = false;

    /**
     * Sets the value of the item
     *
     * @param value the new entity value.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the item.
     */
    public int getValue() {
        return value;
    }

    /**
     * checks if the player hitbox overlaps this item hitbox.
     *
     * @param playerX player x position
     * @param playerY player y position
     * @param playerWidth player width
     * @param playerHeight player height
     * @return true when the player touches the item
     */
    protected boolean isTouchedByPlayer(int playerX, int playerY, int playerWidth, int playerHeight) {
        return playerX < getX() + 20
                && playerX + playerWidth > getX()
                && playerY < getY() + 20
                && playerY + playerHeight > getY();
    }

    /** this collects this penalty item if touched and returns the score loss. */
    public int collectIfTouched(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (collected || !isTouchedByPlayer(playerX, playerY, playerWidth, playerHeight)) {
            return 0;
        }
        collected = true;
        return value;
    }
}
