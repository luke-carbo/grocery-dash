package group18;

import java.awt.event.KeyEvent;
import java.util.Set;

/** this helper converts held keys into player movement and facing direction. */
public class PlayerMovementHelper {

    /** this calculates x and y movement for the current input state. */
    public static int[] getMovementDelta(Set<Integer> keysHeld, int playerSpeed) {
        int dX = 0;
        int dY = 0;

        if (keysHeld.contains(KeyEvent.VK_W)) {
            dY -= playerSpeed;
        }
        if (keysHeld.contains(KeyEvent.VK_S)) {
            dY += playerSpeed;
        }
        if (keysHeld.contains(KeyEvent.VK_D)) {
            dX += playerSpeed;
        }
        if (keysHeld.contains(KeyEvent.VK_A)) {
            dX -= playerSpeed;
        }

        if (dX != 0 && dY != 0) {
            dX /= 1.5;
            dY /= 1.5;
        }

        return new int[]{dX, dY};
    }

    /** this returns the sprite frame index that matches the current movement key. */
    public static int getFrame(Set<Integer> keysHeld, int currentFrame, int FRAME_LEFT, int FRAME_DOWN, int FRAME_UP, int FRAME_RIGHT) {
        if (keysHeld.contains(KeyEvent.VK_W)) {
            return FRAME_UP;
        }
        if (keysHeld.contains(KeyEvent.VK_S)) {
            return FRAME_DOWN;
        }
        if (keysHeld.contains(KeyEvent.VK_D)) {
            return FRAME_RIGHT;
        }
        if (keysHeld.contains(KeyEvent.VK_A)) {
            return FRAME_LEFT;
        }
        return FRAME_DOWN;
    }
}
