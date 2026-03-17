package group18;

/** this helper spawns penalty items with random position and value. */
public class Spawn_Penalty {

    private final Game game;

    /** this constructor stores the game reference used by penalty items. */
    public Spawn_Penalty(Game game) {
        this.game = game;
    }

    /** this creates one penalty item and assigns random spawn data. */
    public Item_Penalty spawnPenalty() {

        Item_Penalty Item = new Item_Penalty(game);

        Item.position_x = randomX();
        Item.position_y = randomY();
        Item.value = randomValue();

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

    /** this returns a random x coordinate inside the playable area. */
    private int randomX() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 600 + 60); // 900 - 120 (Left Wall) - 120 (Right Wall) - 60 (Spacing) + 120(Spacing)
    }

    /** this returns a random y coordinate inside the playable area. */
    private int randomY() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 380 + 120); // 600 - 60 (Top Wall) - 60 (Bottom Wall) + 120 (Spacing)
    }

    /** this returns a random penalty value for the spawned item. */
    private int randomValue() {
        // Random Roll for Spawn Value
        return (int) (Math.random() * 10); // Produces 0 - 10
    }
}
