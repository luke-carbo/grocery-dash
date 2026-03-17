package group18;

/**
 * Bonus items class (Both positive and negative)
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
}
