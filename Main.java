import java.util.HashMap;

/**
 * Main.
 *
 * @author Mei Waterman
 */
public class Main {
    /**
     * Initialise the items hashmap, which is a list of every collectable item and a
     * boolean to represent whether or not the player has collected them, then
     * returns the hashmap. Is put into it's own method for cleanliness purposes.
     *
     * @return returns a hashmap containing every item and a boolean to represent
     *         whether or not the player has collected it.
     */
    static HashMap<String, Boolean> initItems() {
        HashMap<String, Boolean> items = new HashMap<String, Boolean>();
        items.put("lantern", false);
        items.put("oil", false);
        items.put("lighter", false);

        return items;
    }

    /**
     * Main method. Program start and what runs and calls all other methods.
     *
     * @param args
     */
    public static void main(String[] args) {
        // a1 is top-left, e5 is bottom-right. Letters are horizontal, numbers are
        // vertical.
        String playerLocation = "c3";
        boolean play = true;
        HashMap<String, Boolean> items = initItems();

        // Introduction text.
        System.out.println("""
                You awake into darkness. Looking around, you are barely able to make
                out the shape of a room around you. There appears to be a door to the north, another
                one to the south, and a table in the middle of the room, which contains a lantern on
                top of it.\n""");

        // Main game loop.
        while (play) {
            switch (playerLocation) {
                case "c3":
                    if (!items.get("lantern")) {
                        int[] validInputs = { 1, 2, 3, 0 };
                        int choice = Utils.getChoice("""
                                To both the north and south are doors, the northern one wooden
                                while the southern one iron. On the table is a lantern.
                                What do you do?
                                1) Attempt the north door.
                                2) Attempt the south door.
                                3) Pick up the lantern.
                                0) End game
                                """, validInputs);

                        if (choice == 0) {
                            play = false;
                            break;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
