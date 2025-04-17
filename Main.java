import java.util.HashMap;

/**
 * Main.
 */
public class Main {
    public static void main(String[] args) {
        HashMap<String, Boolean> items = new HashMap<String, Boolean>();
        items.put("lantern", false);
        items.put("oil", false);
        items.put("lighter", false);

        // a1 is top-left, e5 is bottom-right. Letters are horizontal, numbers are
        // vertical.
        String playerLocation = "c3";
        boolean play = true;

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
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                To both the north and south are doors, the northern one wooden
                                while the southern one iron. On the table is a lantern.
                                What do you do?
                                1) Attempt the north door.
                                2) Attempt the south door.
                                3) Pick up the lantern.
                                """, validInputs);
                        System.out.println(choice);
                    }
                    break;

                default:
                    break;
            }
            play = false;
        }
    }
}
