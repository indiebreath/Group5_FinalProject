import java.util.HashMap;
import java.util.Scanner;

/**
 * Main.
 *
 * @author Mei Waterman (indiebreath)
 */
public class Main {
    static HashMap<String, Boolean> createPlayerItems() {
        HashMap<String, Boolean> items = new HashMap<String, Boolean>();

        return items;
    }

    /**
     * Method to run combat, loops until either player or opponent is defeated.
     *
     * @param player   the instance of the player character
     * @param opponent the instance of the opponent character
     */
    static void combat(Character player, Character opponent, Scanner scanner) {
        while (player.health > 0 || opponent.health > 0) {
            int[] validInputs = { 1, 2, 3 };
            int choice = Utils.getChoice("What would you like to do?\n1) Attack\n2)Use Item\n3)Run",
                    validInputs, scanner);
            int dmg;

            switch (choice) {
                case 1:
                    dmg = player.attack();
                    System.out.println(player.name + " dealt " + dmg + " to "
                            + opponent.name + ".");
                    opponent.takeDamage(dmg);
                    System.out.println(opponent.name + " is on " + opponent.health
                            + " hit points.");
                    break;

                default:
                    break;
            }

            if (player.health == 0) {
                break;
            }

            dmg = opponent.attack();
            System.out.println(opponent.name + " attacks you for " + dmg);
            player.takeDamage(dmg);
            System.out.println(player.name + " is on " + player.health + " hit points.");
        }
    }

    /**
     * Main method. Program start and what runs and calls all other methods.
     *
     * @param args cli arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        Character player = new Character(name, createPlayerItems(), "c3", 10, 10, 1, 4);
        boolean play = true;

        // Introduction text.
        System.out.println("""
                You awake into darkness. Looking around, you are barely able to make
                out the shape of a room around you. There appears to be a door to the north, another
                one to the south, and a table in the middle of the room, which contains a lantern on
                top of it.\n""");

        combat(player, new Character("opponent", new HashMap<String, Boolean>(), "c3", 10, 10, 1, 4), scanner);
        // Main game loop.
        /*
         * while (play) {
         * switch (player.location) {
         * case "c3":
         * if (!player.items.get("lantern")) {
         * int[] validInputs = { 1, 2, 3, 0 };
         * int choice = Utils.getChoice("""
         * To both the north and south are doors, the northern one wooden
         * while the southern one iron. On the table is a lantern.
         * What do you do?
         * 1) Attempt the north door.
         * 2) Attempt the south door.
         * 3) Pick up the lantern.
         * 0) End game
         * """, validInputs);
         * 
         * if (choice == 0) {
         * play = false;
         * break;
         * }
         * }
         * break;
         * 
         * default:
         * break;
         * }
         * }
         */
    }
}
