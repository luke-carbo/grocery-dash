package group18;

public class Spawn_Additional {

    private final Game game;

    public Spawn_Additional(Game game) {
        this.game = game;
    }

    public Item_Additional spawnAdditional() {

        Item_Additional Item = new Item_Additional(game);

        Item.position_x = randomX();
        Item.position_y = randomY();
        Item.addlclass = randomClass();
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

    private Item_Addl_Class randomClass() {
        // Random Roll for Spawn Region
        int temp = (int) (Math.random() * 100);
        if (temp > 70) { // Or Whatever Bonus Rate
            return Item_Addl_Class.Bonus;
        }

        else {
            return Item_Addl_Class.Penalty;
        }
    }

    private int randomValue() {
        // Random Roll for Spawn Value
        return (int) (Math.random() * 10); // Produces 0 - 10
    }
}
