package group18;

/** this helper spawns bonus items with random position and value. */
public class Spawn_Bonus {

    private final Game game;

    /** this constructor stores the game reference used by bonus items. */
    public Spawn_Bonus(Game game) {
        this.game = game;
    }

    /** this creates one bonus item and assigns random spawn data. */
    public Item_Bonus spawnBonus() {

        Item_Bonus Item = new Item_Bonus(game);

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

    /** this returns a random bonus value for the spawned item. */
    private int randomValue() {
        // Random Roll for Spawn Value
        return (int) (Math.random() * 10); // Produces 0 - 10
    }
}
