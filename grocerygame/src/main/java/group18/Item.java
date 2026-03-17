package group18;

/**
 * Master class for stationary items
 */
public abstract class Item extends Game_Entity {
    /**
     * The entity's score value. Can be negative (Punishments)
     */
    public int value;

    /**
     * The current bonus count.
     */
    public static int bonus_count;

    /**
     * Entity spawn limit for bonus items.
     */
    public static int bonus_limit = 4;

    /**
     * The current penalty count.
     */
    public static int penalty_count;

    /**
     * Entity spawn limit for penalty items.
     */
    public static int penalty_limit = 3;

    /**
     * Enum determining Item Class
     */
    Item_Class type;

    /**
     * Retrieves the score value.
     * @return the item's value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Updates the item's value.
     * @param newValue the new item's value.
     */
    public void setValue(int newValue) {
        value = newValue;
    }

    // Old Limit and Count Getters and Setters
//    /**
//     * Retrieves the current count.
//     * @return the item's current count.
//     */
//    public int getCount() {
//        return count;
//    }
//
//    /**
//     * Retrieves the spawn limit.
//     * @return the item's spawn limit.
//     */
//    public int getLimit() {
//        return limit;
//    }
//
//    /**
//     * Updates the item's spawn limit.
//     * @param newLimit the new item's spawn limit.
//     */
//    public void setLimit(int newLimit) {
//        limit = newLimit;
//    }

    public void destroyEntity() {

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
        return playerX < position_x + 20
                && playerX + playerWidth > position_x
                && playerY < position_y + 20
                && playerY + playerHeight > position_y;
    }
}
