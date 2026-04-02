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
     * Enum determining Item Class
     */
    Item_Class type;

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
