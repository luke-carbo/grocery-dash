package group18;

/**
 * Bonus items class (Both positive and negative)
 */
public class Item_Additional extends Item {

    private final Game game;

    public Item_Additional(Game game) {
        this.game = game;
        type = Item_Class.Additional;
    }

    @Override
    public void collision() {
        game.setScore(game.getScore() + this.value);
//        this.destroyEntity();
    }

    @Override
    public void update() {
        if(this.getCount() < this.getLimit()) {
            // Spawn Method
        }
    }
}
