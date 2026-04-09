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
    private final Image startMenuImage;

    public Game_Painting(Map_Builder mapBuilder, Image[] playerFrames, Image[] securityFrames, Image bonusSprite, Image penaltySprite, Image mainSprite, Image startMenuImage) {
        this.mapBuilder = mapBuilder;
        this.playerFrames = playerFrames;
        this.securityFrames = securityFrames;
        this.bonusSprite = bonusSprite;
        this.mainSprite = mainSprite;
        this.penaltySprite = penaltySprite;
        this.startMenuImage = startMenuImage;
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
            Paint_HUD(g);
            Paint_Start(g);
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
        int panelWidth = g.getClipBounds().width;
        int panelHeight = g.getClipBounds().height;

        if (startMenuImage != null) {
            g.drawImage(startMenuImage, 0, 0, panelWidth, panelHeight, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, panelWidth, panelHeight);
        }

        // Small controls box in top right
        int boxWidth = 220;
        int boxHeight = 140;
        int boxX = panelWidth - boxWidth - 30;
        int boxY = 90;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g.setColor(Color.WHITE);
        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g.setFont(new Font("Verdana", Font.BOLD, 16));
        g.drawString("CONTROLS", boxX + 65, boxY + 25);

        g.setFont(new Font("Tahoma", Font.PLAIN, 15));
        g.drawString("WASD  -   Move", boxX + 30, boxY + 55);
        g.drawString("ESC     -   Pause", boxX + 30, boxY + 80);
        g.drawString("R        -   Restart", boxX + 30, boxY + 105);
        g.drawString("H        -   Highscores", boxX + 30, boxY + 130);

        // Bouncing press enter to play
        long time = System.currentTimeMillis();
        int bounceOffset = (int)(Math.sin(time / 180.0) * 8);

        g.setFont(new Font("Impact", Font.PLAIN, 30));
        g.setColor(Color.WHITE);

        String startText = "PRESS ENTER TO PLAY";
        int textWidth = g.getFontMetrics().stringWidth(startText);
        int textX = (panelWidth - textWidth) / 2;
        int textY = panelHeight - 70 + bounceOffset;

        g.drawString(startText, textX, textY);

    }

    private void Paint_HUD(Graphics g) {
        int width = g.getClipBounds().width;

        g.setColor(new Color(25, 25, 25));
        g.fillRect(0, 0, width, 60);

        g.setColor(new Color(80, 80, 80));
        g.drawLine(0, 60, width, 60);

        drawStatBox(g, 20, 10, "SCORE", "");
        drawStatBox(g, width - 160, 10, "TIME", "");

        String title = "GROCERY DASH";
        g.setFont(new Font("Impact", Font.PLAIN, 28));
        int textWidth = g.getFontMetrics().stringWidth(title);

        g.setColor(Color.BLACK);
        g.drawString(title, (width - textWidth) / 2 + 2, 37);

        g.setColor(new Color(0, 200, 255));
        g.drawString(title, (width - textWidth) / 2, 35);
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
        int width = g.getClipBounds().width;
        drawStatBox(g, 20, 10, "SCORE", String.valueOf(score));
        drawStatBox(g, width - 160, 10, "TIME", elapsedSeconds + "s");
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
        Graphics2D g2d = (Graphics2D) g;

        if (gameOver) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 990, 780);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            int panelWidth = 340;
            int panelHeight = 180;
            int x = (990 - panelWidth) / 2;
            int y = (780 - panelHeight) / 2;

            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, y, panelWidth, panelHeight);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(x, y, panelWidth, panelHeight);

            FontMetrics fm;

            g2d.setFont(new Font("SansSerif", Font.BOLD, 28));
            fm = g2d.getFontMetrics();
            String title = "GAME OVER";
            g2d.drawString(title, x + (panelWidth - fm.stringWidth(title)) / 2, y + 65);

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
            fm = g2d.getFontMetrics();
            String restart = "Press R to Restart";
            g2d.drawString(restart, x + (panelWidth - fm.stringWidth(restart)) / 2, y + 120);
        }

        if (gameWon) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 990, 780);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            int panelWidth = 340;
            int panelHeight = 180;
            int x = (990 - panelWidth) / 2;
            int y = (780 - panelHeight) / 2;

            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, y, panelWidth, panelHeight);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(x, y, panelWidth, panelHeight);

            FontMetrics fm;

            g2d.setFont(new Font("SansSerif", Font.BOLD, 28));
            fm = g2d.getFontMetrics();
            String title = "YOU WIN!";
            g2d.drawString(title, x + (panelWidth - fm.stringWidth(title)) / 2, y + 65);

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
            fm = g2d.getFontMetrics();
            String restart = "Press R to Restart";
            g2d.drawString(restart, x + (panelWidth - fm.stringWidth(restart)) / 2, y + 120);
        }

        if (gamePaused) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, 990, 780);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            int panelWidth = 340;
            int panelHeight = 210;
            int x = (990 - panelWidth) / 2;
            int y = (780 - panelHeight) / 2;

            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, y, panelWidth, panelHeight);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(x, y, panelWidth, panelHeight);

            FontMetrics fm;

            g2d.setFont(new Font("SansSerif", Font.BOLD, 28));
            fm = g2d.getFontMetrics();
            String title = "PAUSED";
            g2d.drawString(title, x + (panelWidth - fm.stringWidth(title)) / 2, y + 60);

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 16));
            fm = g2d.getFontMetrics();

            String resume  = "ESC - Resume";
            String restart = "R - Restart";
            g2d.drawString(resume,  x + (panelWidth - fm.stringWidth(resume))  / 2, y + 120);
            g2d.drawString(restart, x + (panelWidth - fm.stringWidth(restart)) / 2, y + 150);
        }
    }

    private void Paint_Notification(Graphics g, List<Item_Main> mainItems) {
        if (!Item_Main.areAllCollected(mainItems)) {
            g.setColor(Color.WHITE);

            Font font = new Font("DialogInput", Font.BOLD, 16);
            g.setFont(font);

            g.drawString("Collect all items, then go to EXIT", 330, 755);
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
            Font font = new Font("Dialog", Font.BOLD, 48);
            g.setFont(font);
            g.drawString("HIGHSCORES",340, 65);

            Font font2 = new Font("DialogInput", Font.BOLD, 36);
            g.setFont(font2);

            for (int i = 0; i < Highscores.size(); i++) {
                g.drawString(String.valueOf(Highscores_Label.get(i)),410, 210 + (80*i));
                g.drawString(String.valueOf(Highscores.get(i)),510, 210 + (80*i));
            }

            Font font3 = new Font("DialogInput", Font.BOLD, 24);
            g.setFont(font3);

            g.drawString("Press R to Return to the Main Menu",50, 750);
        }
    }
    private void drawStatBox(Graphics g, int x, int y, String label, String value) {
        int w = 140;
        int h = 40;

        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(x, y, w, h, 15, 15);

        g.setColor(new Color(120, 120, 120));
        g.drawRoundRect(x, y, w, h, 15, 15);

        g.setFont(new Font("Verdana", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        g.drawString(label, x + 12, y + 16);

        g.setFont(new Font("Verdana", Font.BOLD, 16));
        g.setColor(Color.RED);
        g.drawString(value, x + 12, y + 33);
    }
}
