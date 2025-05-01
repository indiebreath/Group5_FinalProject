import java.util.Scanner;

/**
 * Main.
 *
 * @author Mei Waterman (indiebreath)
 */
public class Main {

    /**
     * Main method. Program start and what runs and calls all other methods.
     *
     * @param args cli arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Character player = new Character();
        System.out.println("Enter your name:");
        player.setName(scanner.nextLine());
        scanner.close();

        boolean play = true;

        // Introduction text.
        System.out.println("""
                You awake into darkness. Looking around, you are barely able to make
                out the shape of a room around you. There appears to be a door to the north, another
                one to the south, and a table in the middle of the room, which contains a lantern on
                top of it.\n""");

        // Main game loop.
        while (play) {
            switch (player.location) {
                case "c3":
                    if (!player.items.get("lantern")) {
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
