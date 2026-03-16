package group18;

/**
 * Master class for stationary items
 */
public abstract class Item extends Entity_Stationary{
    /**
     * The entity's score value. Can be negative (Punishments)
     */
    public int value;

    /**
     * The current count.
     */
    public static int count;

    /**
     * Entity spawn limit for main and additional.
     */
    public static int limit = 5;

    /**
     * Enum determining Item Class.
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

    /**
     * Retrieves the current count.
     * @return the item's current count.
     */
    public int getCount() {
        return count;
    }

    /**
     * Retrieves the spawn limit.
     * @return the item's spawn limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Updates the item's spawn limit.
     * @param newLimit the new item's spawn limit.
     */
    public void setLimit(int newLimit) {
        limit = newLimit;
    }

    public void destroyEntity() {

    }
}
