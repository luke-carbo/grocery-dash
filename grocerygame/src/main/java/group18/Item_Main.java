package group18;

import java.util.List;

/**
 * Main Items Class (Required Items)
 */
public class Item_Main extends Item{

    private final Game game;
    public boolean collected = false;

    /** this constructor sets the game reference and marks the item as main type. */
    public Item_Main(Game game) {
        this.game = game;
        type = Item_Class.Main;
    }

    /** this will handle what happens when the player collides with this item. */
    @Override
    public void collision() {
        // Check current "Key Count"
        // Check win condition
    }

    /** this updates this item each frame when item logic is needed. */
    @Override
    public void update() {

    }

    /** this collects this item if the player overlaps it and returns the score gained. */
    public int collectIfTouched(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (collected || !isTouchedByPlayer(playerX, playerY, playerWidth, playerHeight)) {
            return 0;
        }
        collected = true;
        return value;
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
