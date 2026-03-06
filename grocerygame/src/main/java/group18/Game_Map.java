package group18;

public class Game_Map extends Game{
    private int tiles_x;
    private int tiles_y;

    /**
     * Calls Map builder to generate map
     */
    public void generateMap(){
        new Map_Builder();
    }
}
