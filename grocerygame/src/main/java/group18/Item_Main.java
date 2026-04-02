package group18;

import java.util.List;

/**
 * Main Items Class (Required Items)
 */
public class Item_Main extends Item{

    /** this constructor sets the game reference and marks the item as main type. */
    public Item_Main(Game game) {
        type = Item_Class.Main;
    }

    /** this returns true when every required main item is already collected. */
    public static boolean areAllCollected(List<Item_Main> items) {
        for (Item_Main item : items) {
            if (!item.collected) {
                return false;
            }
        }
        return true;
    }
}
