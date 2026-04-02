package group18.enemy;

import group18.Player;
import group18.ai.Pathfinder;
import group18.mapCreation.Game_Map;

import java.awt.Point;

/** this enemy type represents a security guard that can catch the player. */
public class SecurityGuard extends Enemy {

    private static final int DEFAULT_SIZE = 30;
    private static final int DEFAULT_MOVE_DELAY = 2;
    private static final double DEFAULT_MOVE_SPEED = 3.0;

    /** this stores chase state across update ticks. */
    public static class ChaseState {
        public Point target;
        public int moveCooldown;
        public int frame;

        /** this creates a chase state with a starting frame. */
        public ChaseState(int initialFrame) {
            this.target = null;
            this.moveCooldown = 0;
            this.frame = initialFrame;
        }
    }

    private int detectionRange;
    private final int enemySize;
    private final int moveDelay;
    private final double moveSpeed;

    /** this constructor places the guard and sets how far it can detect the player. */
    public SecurityGuard(int x, int y, int detectionRange) {
        super(x, y, Enemy_Class.Lethal);
        this.detectionRange = detectionRange;
        this.enemySize = DEFAULT_SIZE;
        this.moveDelay = DEFAULT_MOVE_DELAY;
        this.moveSpeed = DEFAULT_MOVE_SPEED;
    }

    /** this returns the hitbox size used for the guard. */
    public int getEnemySize() {
        return enemySize;
    }

    /** this builds a new chase state object for this guard. */
    public ChaseState createChaseState(int initialFrame) {
        return new ChaseState(initialFrame);
    }

    /** this resets the given chase state to specific values. */
    public void resetChaseState(ChaseState state, Point target, int cooldown, int frame) {
        state.target = target;
        state.moveCooldown = cooldown;
        state.frame = frame;
    }

    /** this marks the guard as actively chasing the player. */
    public void chasePlayer(Player player) {
        this.currentAction = Enemy_Action.Chase;
    }

    /** this updates guard movement against the current player state. */
    public void update(
            Game_Map map,
            Player player,
            ChaseState chaseState,
            int frameLeft,
            int frameDown,
            int frameUp,
            int frameRight
    ) {
        chasePlayer(player);
        moveTowardPlayer(
                map,
                player.getX(),
                player.getY(),
                chaseState,
                frameLeft,
                frameDown,
                frameUp,
                frameRight
        );
    }

    /** this moves the guard toward the player using pathfinding and movement limits. */
    public void moveTowardPlayer(
            Game_Map map,
            int playerX,
            int playerY,
            ChaseState chaseState,
            int frameLeft,
            int frameDown,
            int frameUp,
            int frameRight
    ) {
        double enemyPosX = getX();
        double enemyPosY = getY();

        if (chaseState.target == null) {
            chaseState.moveCooldown++;
            if (chaseState.moveCooldown < this.moveDelay) {
                return;
            }
            chaseState.moveCooldown = 0;

            Point nextStep = Pathfinder.getNextStep(
                    map,
                    getX(),
                    getY(),
                    playerX,
                    playerY
            );

            if (nextStep == null || !canMoveTo(map, nextStep.x, nextStep.y, this.enemySize, this.enemySize)) {
                return;
            }

            chaseState.target = nextStep;
        }

        double dx = chaseState.target.x - enemyPosX;
        double dy = chaseState.target.y - enemyPosY;
        double distance = Math.hypot(dx, dy);

        if (distance < 0.001) {
            chaseState.target = null;
            return;
        }

        if (Math.abs(dx) > Math.abs(dy)) {
            chaseState.frame = dx > 0 ? frameRight : frameLeft;
        } else {
            chaseState.frame = dy > 0 ? frameDown : frameUp;
        }

        double step = Math.min(this.moveSpeed, distance);
        int nextX = (int) Math.round(enemyPosX + (dx / distance) * step);
        int nextY = (int) Math.round(enemyPosY + (dy / distance) * step);

        if (canMoveTo(map, nextX, nextY, this.enemySize, this.enemySize)) {
            setX(nextX);
            setY(nextY);
        } else {
            chaseState.target = null;
            return;
        }

        if (Math.abs(getX() - chaseState.target.x) <= 1
                && Math.abs(getY() - chaseState.target.y) <= 1) {
            setX(chaseState.target.x);
            setY(chaseState.target.y);
            chaseState.target = null;
        }
    }

    /** this checks if the guard overlaps the player hitbox. */
    public boolean overlapsPlayer(int playerX, int playerY, int playerWidth, int playerHeight) {
        return playerX < getX() + this.enemySize
                && playerX + playerWidth > getX()
                && playerY < getY() + this.enemySize
                && playerY + playerHeight > getY();
    }

    /** this checks collision with the player entity and hitbox size. */
    public boolean collidesWithPlayer(Player player, int playerWidth, int playerHeight) {
        return overlapsPlayer(player.getX(), player.getY(), playerWidth, playerHeight);
    }

    /** this checks whether a movement rectangle is inside walkable map tiles. */
    private boolean canMoveTo(Game_Map map, int x, int y, int width, int height) {
        return !map.isSolid(x, y)
                && !map.isSolid(x + width - 1, y)
                && !map.isSolid(x, y + height - 1)
                && !map.isSolid(x + width - 1, y + height - 1);
    }

    /** this checks whether the guard and player are on the same position. */
    public boolean catchesPlayer(Player player) {
        return this.position_x == player.getX() && this.position_y == player.getY();
    }
}
