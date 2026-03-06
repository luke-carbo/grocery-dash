package group18;

public class Item_Main extends Item{

    private final Game game;

    public Item_Main(Game game) {
        this.game = game;
        type = Item_Class.Main;
    }

    @Override
    public void collision() {
        // Check current "Key Count"
        // Check win condition
    }

    @Override
    public void update() {

    }
}
