package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;

/**
 * Class contains constructors and spawn parameters for new {@link Item_Main}.
 */
public class Spawn_Main {

    private final Game game;
    private final Map_Builder map;

    /**
     * Constructs a Spawn_Main object.
     *
     * @param game the main game instance
     * @param map  the game map used for determining valid spawn positions
     */
    public Spawn_Main(Game game, Map_Builder map) {
        this.game = game;
        this.map = map;
    }

    /**
     * Creates and returns a new {@link Item_Main} at a valid map location.
     *
     * @return a newly spawned Item_Main object
     */
    public Item_Main spawnMain() {

        Item_Main Item = new Item_Main(game);

        Point point = SpawnHelper.randomMapTile(map);

        Item.setX(point.x);
        Item.setY(point.y);

        Item.value = 50;

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }
}
