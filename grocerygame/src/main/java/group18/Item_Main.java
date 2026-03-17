package group18;

/** this class represents a main collectible item in the game. */
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
}
