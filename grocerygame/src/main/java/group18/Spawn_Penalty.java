package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;
import java.util.Set;

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
    public Item_Penalty spawnPenalty(Set<Point> occupied) {

        Item_Penalty Item = new Item_Penalty(game);

        Point point = SpawnHelper.randomMapTile(map, occupied);

        Item.setX(point.x);
        Item.setY(point.y);

        Item.value = -SpawnHelper.randomValue();

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

}
