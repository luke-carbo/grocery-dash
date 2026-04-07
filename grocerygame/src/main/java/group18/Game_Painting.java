package group18;

import group18.enemy.SecurityGuard;
import group18.mapCreation.Map_Builder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.security.PrivateKey;
import java.util.List;

public class Game_Painting {

    private final Map_Builder mapBuilder;
    private final Image[] playerFrames;
    private final Image[] securityFrames;

    public Game_Painting(Map_Builder mapBuilder, Image[] playerFrames, Image[] securityFrames) {
        this.mapBuilder = mapBuilder;
        this.playerFrames = playerFrames;
        this.securityFrames = securityFrames;
    }

    public void Paint(
            Graphics g,
            boolean gameStarted,
            boolean gameOver,
            boolean gameWon,
            boolean gamePaused,
            int score,
            long startTime,
            long endTime,
            Player player,
            int playerSize,
            SecurityGuard securityGuard,
            SecurityGuard.ChaseState chaseState,
            List<Item_Main> mainItems,
            List<Item_Bonus> bonusItems,
            List<Item_Penalty> penaltyItems,
            List<Integer> Highscores,
            boolean showHighscores
    ) {
        mapBuilder.draw(g);

        if (!gameStarted) {
            Paint_Start(g);
            Paint_HUD(g);
            Paint_Scores(g, showHighscores, Highscores);
            return;
        }

        long currentTime = (gameOver || gameWon) ? endTime : System.currentTimeMillis();
        long elapsedSeconds = (currentTime - startTime) / 1000;

        Paint_HUD(g);
        Start_Stats(g, score, elapsedSeconds);
        Paint_Enemy(g, securityGuard, chaseState);
        Paint_Items(g, mainItems, bonusItems, penaltyItems);
        Paint_Player(g, player, playerSize);
        Paint_Overlay(g, gameOver, gameWon, gamePaused);
        Paint_Notification(g, mainItems);
    }

    private void Paint_Start(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("CMPT 276 Grocery Game", 320, 250);
        g.drawString("Press ENTER to Start",   335, 280);
        g.drawString("Controls: WASD to move", 330, 310);
        g.drawString("ESC = Pause, R = Restart", 325, 340);
    }

    private void Paint_HUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 810, 60);
        g.setColor(Color.BLACK);
        g.drawLine(0, 60, 810, 60);

        Paint_Stats(g, 10, 10, "SCORE", "");
        Paint_Stats(g, 680, 10, "TIME", "");

        g.setColor(Color.BLUE);
        g.drawString("CMPT 276 GROCERY GAME", 320, 30);
    }

    private void Paint_Stats(Graphics g, int x, int y, String label, String value) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, 120, 30);
        g.drawRect(x, y, 120, 30);

        g.setColor(Color.RED);
        g.drawString(label, x + 10, y + 11);
        g.drawString(value, x + 10, y + 24);
    }

    private void Start_Stats(Graphics g, int score, long elapsedSeconds) {
        Paint_Stats(g, 10, 10, "SCORE", String.valueOf(score));
        Paint_Stats(g, 680, 10, "TIME", elapsedSeconds + "s");
    }

    private void Paint_Enemy(Graphics g, SecurityGuard securityGuard, SecurityGuard.ChaseState chaseState) {
        if (securityFrames != null && securityFrames.length > 0) {
            g.drawImage(
                    securityFrames[chaseState.getFrame()],
                    securityGuard.getX(), securityGuard.getY(),
                    securityGuard.getEnemySize(), securityGuard.getEnemySize(),
                    null
            );
        } else {
            g.setColor(Color.RED);
            g.fillRect(
                    securityGuard.getX(), securityGuard.getY(),
                    securityGuard.getEnemySize(), securityGuard.getEnemySize()
            );
        }
    }

    private void Paint_Items(Graphics g, List<Item_Main> mainItems,
                           List<Item_Bonus> bonusItems, List<Item_Penalty> penaltyItems) {
        g.setColor(Color.YELLOW);
        for (Item_Main item : mainItems)    if (!item.collected) g.fillRect(item.getX(), item.getY(), 20, 20);

        g.setColor(Color.GREEN);
        for (Item_Bonus item : bonusItems)  if (!item.collected) g.fillRect(item.getX(), item.getY(), 20, 20);

        g.setColor(Color.RED);
        for (Item_Penalty item : penaltyItems) if (!item.collected) g.fillRect(item.getX(), item.getY(), 20, 20);
    }

    private void Paint_Player(Graphics g, Player player, int playerSize) {
        if (playerFrames != null && playerFrames.length > 0) {
            g.drawImage(playerFrames[player.getCurrentFrame()],
                    player.getX(), player.getY(), playerSize, playerSize, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(player.getX(), player.getY(), playerSize, playerSize);
        }
    }

    private void Paint_Overlay(Graphics g, boolean gameOver, boolean gameWon, boolean gamePaused) {
        g.setColor(Color.WHITE);
        if (gameOver) {
            g.drawString("GAME OVER",          380, 300);
            g.drawString("Press R to Restart", 360, 330);
        }
        if (gameWon) {
            g.drawString("YOU WIN",            390, 300);
            g.drawString("Press R to Restart", 360, 330);
        }
        if (gamePaused) {
            g.drawString("PAUSED",                 390, 280);
            g.drawString("Press ESC to Resume",    350, 310);
            g.drawString("Press R to Restart",     355, 340);
        }
    }

    private void Paint_Notification(Graphics g, List<Item_Main> mainItems) {
        if (!Item_Main.areAllCollected(mainItems)) {
            g.setColor(Color.WHITE);
            g.drawString("Collect all items, then go to EXIT", 290, 575);
        }
    }

    private void Paint_Scores(Graphics g, boolean showHighscores, List<Integer> Highscores) {
        if (showHighscores) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 810, 660);
            g.setColor(Color.BLACK);
            g.drawString("HIGHSCORES",355, 180);
            for (int i = 0; i < Highscores.size(); i++) {
                g.drawString(String.valueOf(Highscores.get(i)),390, 210 + (30*i));
            }
            g.drawString("PRESS R TO RETURN TO GAME",310, 390);
        }
    }
}