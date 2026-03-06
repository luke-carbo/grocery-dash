package group18;

public class Item_Additional extends Item {

    private final Game game;

    public Item_Additional(Game game) {
        this.game = game;
        this.type = Item_Class.Additional;
    }

    @Override
    public void collision() {
        game.setScore(game.getScore() + this.value);
        this.destroyEntity();
    }

    @Override
    public void update() {
        // Get Count
        // Check against Limit
        // Call Spawning Method
    }
}
