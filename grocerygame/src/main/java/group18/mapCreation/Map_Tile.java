package group18.mapCreation;

public class Map_Tile {

    private Tile_Type type;

    Map_Tile(String s) {
        switch (s) {
            case "w":
                type = Tile_Type.Wall;
                break;
            case "f":
                type = Tile_Type.Floor;
                break;
        }
    }

    public boolean isSolid() {
        return type != null && type.isSolid();
    }

    public Tile_Type getTileType() {
        return type;
    }
}