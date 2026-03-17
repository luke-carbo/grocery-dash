package group18;

import java.awt.event.KeyEvent;
import java.util.Set;

public class PlayerMovementHelper {

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