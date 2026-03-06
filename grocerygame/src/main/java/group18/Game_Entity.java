package group18;

public abstract class Game_Entity {

    protected int position_x;
    protected int position_y;

    public abstract void collision();

    public abstract void update();
}