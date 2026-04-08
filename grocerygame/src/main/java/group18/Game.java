package group18;


import javax.swing.JFrame;
import java.awt.*;

/**
 * Main game class for Grocery Game.
 */
public class Game extends JFrame {
    public int score;
    private int highScore;
    private int stopwatch;

    public Game() {
        setTitle("CMPT276 Grocery Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);

        setVisible(true);
        pack();
        setLocationRelativeTo(null);


    }

    /**
     * Getter for the current score.
     * @return current score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Setter for the current score.
     * @param newScore new score value
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Starts the game?
     */
    public void start() {

    }

    /**
     * Stops the game on a loss?
     */
    public void endLoss() {

    }

    /**
     * Stops the game on a win?
     */
    public void endWin() {

    }

    /**
     * High score getter
     * @return Current High score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Stopwatch getter
     * @return Current stopwatch value
     */
    public int getStopwatch() {
        return stopwatch;
    }
}
