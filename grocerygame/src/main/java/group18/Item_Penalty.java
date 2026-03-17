package group18;

/**
 * Bonus items class (Negative Points)
 */
public class Item_Penalty extends Item {

    private final Game game;
    public boolean collected = false;

    public Item_Penalty(Game game) {
        this.game = game;
        type = Item_Class.Penalty;
        this.penalty_count += 1;
    }

    @Override
    public void collision() {
        game.setScore(game.getScore() + this.value);
        destroyEntity();
    }

    @Override
    public void update() {
        // TBD
    }

    /** this collects this penalty item if touched and returns the score loss. */
    public int collectIfTouched(int playerX, int playerY, int playerWidth, int playerHeight) {
        if (collected || !isTouchedByPlayer(playerX, playerY, playerWidth, playerHeight)) {
            return 0;
        }
        collected = true;
        Item.penalty_count -= 1;
        return -value;
    }
}
