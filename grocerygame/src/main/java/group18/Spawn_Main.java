package group18;

public class Spawn_Main {

    private final Game game;

    public Spawn_Main(Game game) {
        this.game = game;
    }

    public Item_Main Spawn_Main() {

        Item_Main Item = new Item_Main(game);

//        Item.position_x = randomX();
//        Item.position_y = randomY();

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

//    private int randomX() {
//        // Random Roll for Spawn Region
//        return (int) (Math.Random() * // Spawnable Region X);
//    }
//
//    private int randomY() {
//        // Random Roll for Spawn Region
//        return (int) (Math.Random() * // Spawnable Region Y);
//    }
}
