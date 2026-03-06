package group18;

/**
 * Main game class for Grocery Game.
 */
public class Game {
    public int score;
    private int highScore;
    private int stopwatch;

    /**
     * Getter for the current score.
     * @return current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the current score.
     * @param newScore new score value
     */
    public void setScore(int newScore) {
        score = newScore;
    }

    /**
     * Starts the game?
     */
    public void start(){

    }

    /**
     * Stops the game on a loss?
     */
    public void endLoss(){

    }

    /**
     * Stops the game on a win?
     */
    public void endWin(){

    }

    /**
     * High score getter
     * @return Current High score
     */
    public int getHighScore() {return highScore; }

    /**
     * Stopwatch getter
     * @return Current stopwatch value
     */
    public int getStopwatch() {
        return stopwatch;
    }
    
}
