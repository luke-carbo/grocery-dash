package group18;

import javax.swing.JFrame;

public class Game extends JFrame {

    private int score;
    private int highScore;
    private int stopwatch;

    public Game() {

        setTitle("CMPT276 Grocery Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public int getScore() {
        return score;
    }

    public void start(){

    }

    public void endLoss(){

    }

    public void endWin(){

    }

    public int getHighScore() {
        return highScore;
    }

    public int getStopwatch() {
        return stopwatch;
    }
}