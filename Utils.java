import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Utils.
 *
 * <p>
 * A generic file containing functions that may be used regularly across the
 * projected, all separated into their own file in order to avoid clogging up
 * the main class with misceallaneous functions.
 * </p>
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
    public static boolean containsInt(final int[] array, final int key) {
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
    public static int getChoice(String prompt, int[] validInputs, Scanner scanner) {
        int input = -1;

        while (!containsInt(validInputs, input)) {
            System.out.println(prompt);
            input = scanner.nextInt();
        }

        return input;
    }

    /**
     * Method to get the intersection between an inventory hashmap and an array of
     * strings, but only adds the intersection key if it's value is true. Primarily
     * used in the combat loop to get a list of usable items that the player
     * actually has access to.
     *
     * @param inventory  the inventory to search through, in the combat loop would
     *                   be the player's inventory.
     * @param searchKeys an array containing all of the keys that it should search
     *                   the inventory for, in the combat loop would be an array of
     *                   all usable combat items.
     * @return return all items from searchKeys that exist in inventory, and whose
     *         values in inventory are true.
     */
    public static String[] getItemIntersections(HashMap<String, Boolean> inventory,
            String[] searchKeys) {
        String[] intersections = new String[searchKeys.length];
        int index = 0;
        for (String x : searchKeys) {
            if (inventory.containsKey(x) && inventory.get(x)) {
                intersections[index] = x;
                index++;
            }
        }

        return intersections;
    }

    /**
     * Method to generate random alphanumeric string of a given length.
     *
     * @param length the length of the string to generate
     * @return the generated string
     */
    public static String generateAlphanumericString(int length) {
        // numeral '0'
        int leftLimit = 48;
        // letter 'z'
        int rightLimit = 122;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
