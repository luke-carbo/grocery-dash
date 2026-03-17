package group18;

import group18.mapCreation.Game_Map;

import java.awt.*;

/**
 * Class contains constructors and spawn parameters for new {@link Item_Main}.
 */
public class Spawn_Main {

    private final Game game;
    private final Game_Map map;

    /**
     * Constructs a Spawn_Main object.
     *
     * @param game the main game instance
     * @param map  the game map used for determining valid spawn positions
     */
    public Spawn_Main(Game game, Game_Map map) {
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

//        Item.position_x = randomX();
//        Item.position_y = randomY();

        Point point = randomMapTile();

        Item.position_x = point.x;
        Item.position_y = point.y;

        Item.value = 50;

        // Conditional Sprite and Value terms based on Spawn Region

        return Item;
    }

    /**
     * Generates a random valid position on the map.
     * <p>
     * A valid position is defined as a tile that is not solid (i.e., walkable).
     * The method continuously searches until a valid tile is found.
     *
     * @return a Point representing the pixel coordinates of a valid tile
     */
    private Point randomMapTile() {

        int tileSize = map.getTileSize();

        while (true) {
            int row = (int)(Math.random() * map.getRows());
            int col = (int)(Math.random() * map.getCols());

            if (!map.isSolidTile(row, col)) {

                int x = col * tileSize;
                int y = row * tileSize;

                return new Point(x, y);
            }
        }
    }

    /**
     * Generates a random X coordinate within a predefined range.
     * <p>
     * Currently unused. Originally intended for non-map-based spawning.
     *
     * @return a random X coordinate
     */
    private int randomX() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 600 + 60); // 900 - 120 (Left Wall) - 120 (Right Wall) - 60 (Spacing) + 120(Spacing)
    }

    /**
     * Generates a random Y coordinate within a predefined range.
     * <p>
     * Currently unused. Originally intended for non-map-based spawning.
     *
     * @return a random Y coordinate
     */
    private int randomY() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 380 + 120); // 600 - 60 (Top Wall) - 60 (Bottom Wall) + 120 (Spacing)
    }

}
