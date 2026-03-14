package group18.mapCreation;

/**
 * Describes all tile types
 * Describes if solid or not
 * Describes symbol for reading from a string
 */
public enum Tile_Type {
    Wall(true), Floor(false), Shelf (true);

    private final boolean solid;

    Tile_Type(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }
}