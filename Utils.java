import java.util.Arrays;
import java.util.Scanner;

/**
 * A generic file containing functions that may be used regularly across the
 * projected, all separated into their own file in order to avoid clogging up
 * the main class with misceallaneous functions.
 *
 * @author Mei Waterman
 */
public class Utils {
    /**
     * Generic contains method to check if an array contains a certain value.
     *
     * @param array the array to look through.
     * @param key   the value to search for.
     * @return returns whether or not the key is in the array.
     */
    public static boolean contains(final int[] array, final int key) {
        return Arrays.stream(array).anyMatch(i -> i == key);
    }

    /**
     * Called when prompting the player for input, repeatedly printing the prompt
     * until the user inputs a value that is contained within the validInputs array,
     * then returns the valid input.
     *
     * @param prompt      string prompt to tell the user what their environment is,
     *                    and what options they can take.
     * @param validInputs an array of the valid numbers they can input. Cannot
     *                    contain -1.
     * @return returns what the user selected, if it was valid.
     */
    public static int getChoice(String prompt, int[] validInputs) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;

        while (!contains(validInputs, input)) {
            System.out.println(prompt);
            input = scanner.nextInt();
        }

        scanner.close();
        return input;
    }

    /**
     * Generic clamp method to ensure that the value val remains in between the
     * values of min and max. Implemented here due to similar method not existing in
     * standard libraries.
     *
     * @param val the value to compare and ensure remains within the min and max
     *            values.
     * @param min minimum value that val variable can be.
     * @param max maximum value that val variable can be.
     * @return return the resulting value, whether it be unchanged, increased, or lowered.
     */
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
