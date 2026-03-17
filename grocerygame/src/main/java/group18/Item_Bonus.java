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
        this.bonus_count += 1;
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
