package group18;

public class Spawn_Penalty {

    private final Game game;

    public Spawn_Penalty(Game game) {
        this.game = game;
    }

    public Item_Penalty spawnPenalty() {

        Item_Penalty Item = new Item_Penalty(game);

        Item.position_x = randomX();
        Item.position_y = randomY();
        Item.value = randomValue();

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

    private int randomX() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 600 + 60); // 900 - 120 (Left Wall) - 120 (Right Wall) - 60 (Spacing) + 120(Spacing)
    }

    private int randomY() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 380 + 120); // 600 - 60 (Top Wall) - 60 (Bottom Wall) + 120 (Spacing)
    }

    private int randomValue() {
        // Random Roll for Spawn Value
        return (int) (Math.random() * 10); // Produces 0 - 10
    }
}
