package group18.mapCreation;

import group18.Game;

import java.awt.*;

public class Game_Map {
    private Map_Builder map_builder;

    public Game_Map() {
        generateMap();
    }
    /**
     * Calls Map builder to generate map
     */
    public void generateMap(){
        map_builder = new Map_Builder();
    }
    public void draw(Graphics g){
        map_builder.draw(g);
    }


}
