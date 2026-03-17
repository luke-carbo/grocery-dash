package group18;

/** this helper contains win condition checks used by the game panel. */
public final class GameRulesHelper {

    private GameRulesHelper() {
    }

    /** this checks whether the player is touching the exit area. */
    public static boolean isPlayerAtExit(
            int playerX,
            int playerY,
            int playerWidth,
            int playerHeight,
            int exitX,
            int exitY,
            int exitSize
    ) {
        return playerX < exitX + exitSize
                && playerX + playerWidth > exitX
                && playerY < exitY + exitSize
                && playerY + playerHeight > exitY;
    }

    /** this checks if the game should be marked as won right now. */
    public static boolean hasWon(
            boolean alreadyWon,
            boolean allMainItemsCollected,
            boolean playerAtExit) {
        return !alreadyWon && allMainItemsCollected && playerAtExit;
    }
}
