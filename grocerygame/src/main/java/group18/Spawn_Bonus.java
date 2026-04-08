package group18;

import group18.mapCreation.Map_Builder;

import java.awt.*;
import java.util.Set;

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
    public Item_Bonus spawnBonus(Set<Point> occupied) {

        Item_Bonus Item = new Item_Bonus(game);

        Point point = SpawnHelper.randomMapTile(map, occupied);

        Item.setX(point.x);
        Item.setY(point.y);

        Item.value = SpawnHelper.randomValue();

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

}
