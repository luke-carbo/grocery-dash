package group18.enemy;
import group18.Player;

public class SecurityGuard extends Enemy {

    private int detectionRange;

    public SecurityGuard(int x, int y, int detectionRange) {
        super(x, y, Enemy_Class.Lethal);
        this.detectionRange = detectionRange;
    }

    public void chasePlayer(Player player) {
        //TODO (idk if we actually have to code this here but it needs to be done somewhere)
    }

    public boolean catchesPlayer(Player player){
        return this.position_x == player.getX() && this.position_y == player.getY();
    }

    @Override
    public void update(){
        //TODO
    }

    @Override
    public void collision(){
        //TODO
    }
}