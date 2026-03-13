package group18.mapCreation;

import java.awt.*;

public class Map_Builder {


    private Map_Tile[][] tile_Map;
    /**
     * Creates map with all tiles
     * Iterates through a string -> displays graphics in correct position
     */
    public Map_Builder(){
        String[] map = {"fwfwffffwwffffwfwwww",
                        "wwwfffffwwffwwfwfwff",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",
                        "fwfwffffwwffffwfwwww",};
        int rows = map.length;
        int cols = 0;
        for (char c : map[0].toCharArray()) {
            cols++;
        }

        tile_Map = new Map_Tile[rows][cols];

        for (int i=0; i<map.length; i++){
            for (int j=0; j<map[i].length(); j++){
                char c = map[i].charAt(j);
                Map_Tile tile = new Map_Tile(c);
                tile_Map[i][j] = tile;
            }
        }
    }
    public void draw(Graphics g){
        for (int i =0; i<tile_Map.length; i++){
            for (int j =0; j<tile_Map[i].length; j++){
                tile_Map[i][j].draw(g, i, j);
            }
        }
    }
}
