import java.util.*;

public class DiceGame {

    private final Random random;
    private final int[] scores;
    private final int totalNumDice;
    private int numDice;
    private int total;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid command arguments : [num_game] [num_dice]");
            return;
        }

        int numGames = Integer.parseInt(args[0]);

        if (numGames <= 0) {
            System.out.println("Invalid command argument : num_game must be greater than 0");
            return;
        }

        int numDice = Integer.parseInt(args[1]);

        if (numDice <= 0) {
            System.out.println("Invalid command argument : num_dice must be greater than 0");
            return;
        }

        new DiceGame(numDice).runSimulations(numGames);
    }

    public DiceGame(int numDice) {
        random = new Random();
        scores = new int[6 * numDice + 1];
        totalNumDice = numDice;
    }

    private void runSimulations(int numGames) {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numGames; ++i) {
            runGame();
        }

        float totalTime = (System.currentTimeMillis() - startTime) / 1000.0f;

        System.out.printf("%nNumber of simulations was %d using %d dice.%n%n", numGames, totalNumDice);

        for (int i = 0; i < scores.length; ++i) {
            System.out.printf("Total %d occurs %.4f occurred %d times.%n", i, (float) scores[i] / numGames, scores[i]);
        }

        System.out.printf("%nTotal simulation took %f seconds.%n", totalTime);
    }

    private void runGame() {
        numDice = totalNumDice;
        total = 0;

        while (numDice > 0) {
            rollDice();
        }

        ++scores[total];
    }

    private void rollDice() {
        int numThrees = 0;
        int minValue = 6;

        for (int i = 0; i < numDice; ++i) {
            int value = random.nextInt(6) + 1;

            if (value == 3) {
                ++numThrees;
            } else if (value < minValue) {
                minValue = value;
            }
        }

        if (numThrees > 0) {
            numDice -= numThrees;
        } else {
            --numDice;
            total += minValue;
        }
    }
}
