package group18;

import group18.mapCreation.Game_Map;

import java.awt.*;

public class Spawn_Main {

    private final Game game;
    private final Game_Map map;

    public Spawn_Main(Game game, Game_Map map) {
        this.game = game;
        this.map = map;
    }

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

    private int randomX() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 600 + 60); // 900 - 120 (Left Wall) - 120 (Right Wall) - 60 (Spacing) + 120(Spacing)
    }

    private int randomY() {
        // Random Roll for Spawn Region
        return (int) (Math.random() * 380 + 120); // 600 - 60 (Top Wall) - 60 (Bottom Wall) + 120 (Spacing)
    }

}
