package group18.mapCreation;

public class Map_Builder extends Game_Map {
    /**
     * Creates map with all tiles
     * Iterates through a string -> displays graphics in correct position
     */
    public Map_Builder(){
        String[] map = {"fwfwffffwwfff",
                        "wwwfffffwwffw"};
        for (int i=0; i<map.length; i++){
            Map_Tile tile = new Map_Tile(map[i]);
        }
    }
}
