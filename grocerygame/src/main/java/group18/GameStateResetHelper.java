package group18;

import group18.enemy.SecurityGuard;
import group18.Score_Tracker;

import java.util.List;

/** this helper builds a full default state snapshot used to reset gameplay. */
public class GameStateResetHelper {

    /** this data object stores all values needed to restore a fresh game state. */
    public static class ResetStateData {
        public int playerX;
        public int playerY;

        public int score;
        public boolean score_saved;
        public long startTime;
        public long endTime;
        public List<Integer> Highscores;

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

    /** this creates and returns a reset data object with default starting values. */
    public static ResetStateData createResetState(int FRAME_DOWN) {
        ResetStateData data = new ResetStateData();

        data.playerX = 165;
        data.playerY = 730;

        data.score = 0;
        data.score_saved = false;
        data.startTime = 0;
        data.endTime = 0;
        data.Highscores = Score_Tracker.loadScore();

        data.gameOver = false;
        data.gameWon = false;
        data.gameStarted = false;
        data.gamePaused = false;

        data.currentFrame = FRAME_DOWN;
        data.securityCurrentFrame = FRAME_DOWN;

        data.securityGuard = new SecurityGuard(500, 350, 100);
        data.enemyPosX = data.securityGuard.getX();
        data.enemyPosY = data.securityGuard.getY();
        data.enemyTarget = null;
        data.enemyMoveCooldown = 0;

        return data;
    }
}
