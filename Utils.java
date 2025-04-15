import java.util.Arrays;
import java.util.Scanner;

/**
 * A generic file containing functions that may be used regularly across the
 * projected, all separated into their own file in order to avoid clogging up
 * the main class with misceallaneous functions.
 */
public class Utils {
    public static boolean contains(final int[] array, final int key) {
        return Arrays.stream(array).anyMatch(i -> i == key);
    }

    /**
     * Called when prompting the player for input, with the prompt variable being
     * used to tell the player what options they have, and the validInputs variable
     * being used to verify whether the given input is valid. Will loop until a
     * valid input is given then return the input.
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
