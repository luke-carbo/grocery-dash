package group18;

/**
 * Bonus items class (Additional Points)
 */
public class Item_Bonus extends Item {

    private final Game game;
    public boolean collected = false;

    public Item_Bonus(Game game) {
        this.game = game;
        type = Item_Class.Bonus;
    }

    /** this collects this bonus item if touched and returns the score gained. */
    public int collectIfTouched(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (collected || !isTouchedByPlayer(playerX, playerY, playerWidth, playerHeight)) {
            return 0;
        }
        collected = true;
        return value;
    }
}
