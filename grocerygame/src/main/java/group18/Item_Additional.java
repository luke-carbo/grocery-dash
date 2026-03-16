package group18;

/**
 * Bonus items class (Both positive and negative)
 */
public class Item_Additional extends Item {

    private final Game game;
    public Item_Addl_Class addlclass;

    public Item_Additional(Game game) {
        this.game = game;
        type = Item_Class.Additional;
    }

    @Override
    public void collision() {
        game.setScore(game.getScore() + this.value);
        destroyEntity();
    }

    @Override
    public void update() {
        if(this.getCount() < this.getLimit()) {
            new Spawn_Additional(game);
        }
    }
}
