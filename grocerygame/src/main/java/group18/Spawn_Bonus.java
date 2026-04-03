package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;

/**
 * Class contains constructors and spawn parameters for new {@link Item_Bonus}.
 */
public class Spawn_Bonus {

    private final Game game;
    private final Map_Builder map;

    /**
     * Constructs a Spawn_Bonus object.
     *
     * @param game the main game instance
     * @param map  the game map used for determining valid spawn positions
     */
    public Spawn_Bonus(Game game, Map_Builder map) {
        this.game = game;
        this.map = map;
    }

    /**
     * Creates and returns a new {@link Item_Bonus} at a valid map location.
     *
     * @return a newly spawned Item_Bonus object
     */
    public Item_Bonus spawnBonus() {

        Item_Bonus Item = new Item_Bonus(game);

//        Item.position_x = randomX();
//        Item.position_y = randomY();

        Point point = SpawnHelper.randomMapTile(map);

        Item.setX(point.x);
        Item.setY(point.y);

        Item.value = randomValue();

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

    /**
     * Generates a random Item Value within a predefined range.
     * <p>
     * @return a random Item Value
     */
    private int randomValue() {
        // Random Roll for Spawn Value
        return (int) (Math.random() * 10); // Produces 0 - 10
    }
}
