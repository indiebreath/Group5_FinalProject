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
}
