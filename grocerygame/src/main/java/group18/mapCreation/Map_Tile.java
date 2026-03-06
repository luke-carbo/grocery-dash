package group18.mapCreation;

import group18.Game_Map;

public class Map_Tile extends Game_Map {
    private boolean solid;
    private Tile_Type type;

    Map_Tile(String s){
        switch(s){
            case "w":
                type = Tile_Type.Wall;
                break;
            case "f":
                type = Tile_Type.Floor;
                break;
        };
    }
    public boolean isSolid() {
        return solid;
    }
    public Tile_Type getType() {
        return type;
    }


}
