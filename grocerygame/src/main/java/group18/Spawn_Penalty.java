package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;

/**
 * Class contains constructors and spawn parameters for new {@link Item_Penalty}.
 */
public class Spawn_Penalty {

    private final Game game;
    private final Map_Builder map;

    /**
     * Constructs a Spawn_Penalty object.
     *
     * @param game the main game instance
     * @param map  the game map used for determining valid spawn positions
     */
    public Spawn_Penalty(Game game, Map_Builder map) {
        this.game = game;
        this.map = map;
    }

    /**
     * Creates and returns a new {@link Item_Penalty} at a valid map location.
     *
     * @return a newly spawned Item_Penalty object
     */
    public Item_Penalty spawnPenalty() {

        Item_Penalty Item = new Item_Penalty(game);

        Point point = SpawnHelper.randomMapTile(map);

        Item.position_x = point.x;
        Item.position_y = point.y;

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
