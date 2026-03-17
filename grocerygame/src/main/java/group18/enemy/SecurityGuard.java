package group18.enemy;

import group18.Player;

/** this enemy type represents a security guard that can catch the player. */
public class SecurityGuard extends Enemy {

    private int detectionRange;

    /** this constructor places the guard and sets how far it can detect the player. */
    public SecurityGuard(int x, int y, int detectionRange) {
        super(x, y, Enemy_Class.Lethal);
        this.detectionRange = detectionRange;
    }

    /** this is where guard chase behavior toward the player will be implemented. */
    public void chasePlayer(Player player) {
        // TODO
    }

    /** this checks whether the guard and player are on the same position. */
    public boolean catchesPlayer(Player player) {
        return this.position_x == player.getX() && this.position_y == player.getY();
    }

    /** this runs per-frame update logic for the guard. */
    @Override
    public void update() {
        // TODO
    }

    /** this handles collision behavior for the guard. */
    @Override
    public void collision() {
        // TODO
    }
}
