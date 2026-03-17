package group18;

import java.io.*;
import java.util.*;

/** this utility reads and writes the top scores to a local text file. */
public class Score_Tracker{

    private static final String FILE = "scores.txt";
    private static final int MAX_SCORES = 5;

    /** this loads saved scores from disk and returns them as a list. */
    public static List<Integer> loadScore() {
        List<Integer> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                try {
                    scores.add(line.isEmpty() ? 0 : Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    scores.add(0);
                }
            }

        } catch (IOException e) {
            // file might not exist yet
        }

        return scores;
    }

    /** this adds a new score, keeps the best scores, and saves them to disk. */
    public static void saveScore(int newScore) {

        List<Integer> scores = loadScore();

        scores.add(newScore);

        scores.sort(Collections.reverseOrder());

        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {

            for (int score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }

        } catch (IOException e) {
            // Exception
        }
    }
}
