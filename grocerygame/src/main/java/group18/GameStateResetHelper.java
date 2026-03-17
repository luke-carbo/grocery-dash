package group18;

import group18.enemy.SecurityGuard;

public class GameStateResetHelper {

    public static class ResetStateData {
        public int playerX;
        public int playerY;

        public int score;
        public boolean score_saved;
        public long startTime;
        public long endTime;

        public boolean gameOver;
        public boolean gameWon;
        public boolean gameStarted;
        public boolean gamePaused;

        public int currentFrame;
        public int securityCurrentFrame;

        public SecurityGuard securityGuard;
        public double enemyPosX;
        public double enemyPosY;
        public java.awt.Point enemyTarget;
        public int enemyMoveCooldown;

        public int[] itemX;
        public int[] itemY;
        public boolean[] itemCollected;
    }

    public static ResetStateData createResetState(int FRAME_DOWN) {
        ResetStateData data = new ResetStateData();

        data.playerX = 165;
        data.playerY = 570;

        data.score = 0;
        data.score_saved = false;
        data.startTime = 0;
        data.endTime = 0;

        data.gameOver = false;
        data.gameWon = false;
        data.gameStarted = false;
        data.gamePaused = false;

        data.currentFrame = FRAME_DOWN;
        data.securityCurrentFrame = FRAME_DOWN;

        data.securityGuard = new SecurityGuard(500, 300, 100);
        data.enemyPosX = data.securityGuard.getX();
        data.enemyPosY = data.securityGuard.getY();
        data.enemyTarget = null;
        data.enemyMoveCooldown = 0;

        data.itemX = new int[]{500, 650, 350};
        data.itemY = new int[]{200, 450, 350};
        data.itemCollected = new boolean[]{false, false, false};

        return data;
    }
}