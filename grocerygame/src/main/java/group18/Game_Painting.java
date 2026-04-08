package group18;

import group18.enemy.SecurityGuard;
import group18.mapCreation.Map_Builder;

import java.awt.*;
import java.util.ArrayList;
import java.awt.*;
import java.util.List;

public class Game_Painting {

    private final Map_Builder mapBuilder;
    private final Image[] playerFrames;
    private final Image[] securityFrames;
    private final Image bonusSprite;
    private final Image penaltySprite;
    private final Image mainSprite;

    public Game_Painting(Map_Builder mapBuilder, Image[] playerFrames, Image[] securityFrames, Image bonusSprite, Image penaltySprite, Image mainSprite) {
        this.mapBuilder = mapBuilder;
        this.playerFrames = playerFrames;
        this.securityFrames = securityFrames;
        this.bonusSprite = bonusSprite;
        this.mainSprite = mainSprite;
        this.penaltySprite = penaltySprite;
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
        int panelWidth = 420;
        int panelHeight = 240;

        int x = (g.getClipBounds().width - panelWidth) / 2;
        int y = (g.getClipBounds().height - panelHeight) / 2;

        // Background box (no transparency, solid color)
        g.setColor(Color.BLUE);
        g.fillRect(x, y, panelWidth, panelHeight);

        // Border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, panelWidth, panelHeight);

        // Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.drawString("GROCERY GAME", x + 95, y + 45);

        // Start text
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Press ENTER to Start", x + 105, y + 85);

        // Controls
        g.setFont(new Font("SansSerif", Font.PLAIN, 15));
        g.drawString("WASD  -  Move", x + 130, y + 120);
        g.drawString("ESC   -  Pause", x + 130, y + 145);
        g.drawString("R     -  Restart", x + 130, y + 170);
        g.drawString("H     -  Highscores", x + 130, y + 195);
    }

    private void Paint_HUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, g.getClipBounds().width, 60);
        g.setColor(Color.BLACK);
        g.drawLine(0, 60, g.getClipBounds().width, 60);

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
        for (Item_Main item : mainItems) {
            if (!item.collected) {
                if (mainSprite != null) {
                    g.drawImage(mainSprite, item.getX(), item.getY(), 35, 35, null);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillRect(item.getX(), item.getY(), 20, 20);
                }
            }
        }

        for (Item_Bonus item : bonusItems) {
            if (!item.collected) {
                if (bonusSprite != null) {
                    g.drawImage(bonusSprite, item.getX(), item.getY(), 30, 30, null);
                } else {
                    g.setColor(Color.GREEN);
                    g.fillRect(item.getX(), item.getY(), 20, 20);
                }
            }
        }

        for (Item_Penalty item : penaltyItems) {
            if (!item.collected) {
                if (penaltySprite != null) {
                    g.drawImage(penaltySprite, item.getX(), item.getY(), 33, 33, null);
                } else {
                    g.setColor(Color.RED);
                    g.fillRect(item.getX(), item.getY(), 20, 20);
                }
            }
        }
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
            g.drawString("Collect all items, then go to EXIT", 290, 645);
        }
    }

    private void Paint_Scores(Graphics g, boolean showHighscores, List<Integer> Highscores) {
        if (showHighscores) {
            List <String> Highscores_Label = new ArrayList<>();
            Highscores_Label.add("1st");
            Highscores_Label.add("2nd");
            Highscores_Label.add("3rd");
            Highscores_Label.add("4th");
            Highscores_Label.add("5th");

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);

            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(30,20,930,50,5,5);
            g.fillRoundRect(30,20,930,50,5,5);

            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(30,100,930,600,5,5);
            g.fillRoundRect(30,100,930,600,5,5);

            g.setColor(Color.BLACK);
            Font font = new Font("Dialog", Font.BOLD, 48); // 48pt font
            g.setFont(font);
            g.drawString("HIGHSCORES",340, 65);

            Font font2 = new Font("DialogInput", Font.BOLD, 36); // 48pt font
            g.setFont(font2);

            for (int i = 0; i < Highscores.size(); i++) {
                g.drawString(String.valueOf(Highscores_Label.get(i)),410, 210 + (60*i));
                g.drawString(String.valueOf(Highscores.get(i)),510, 210 + (60*i));
            }

            Font font3 = new Font("DialogInput", Font.BOLD, 24); // 48pt font
            g.setFont(font3);

            g.drawString("Press R to Return to the Main Menu",50, 750);
        }
    }
}
