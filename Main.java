import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Main.
 *
 * @author Mei Waterman (indiebreath)
 */
public class Main {
    static HashMap<String, Boolean> createPlayerItems() {
        HashMap<String, Boolean> items = new HashMap<String, Boolean>();
        items.put("flashlight", false);
        items.put("crowbar", false);

        return items;
    }

    /**
     * Method to run combat, loops until either player or opponent is defeated.
     *
     * @param player   the instance of the player character
     * @param opponent the instance of the opponent character
     */
    static void combat(Character player, Character opponent, Scanner scanner) {
        Random rand = new Random();
        String[] usableItems = { "potion" };

        // loops combat until either the player or opponent are dead (at 0 hit points)
        while (player.health > 0 || opponent.health > 0) {
            // prompt user for choice regarding what they would like to do during their turn
            // additionally initialise dmg variable
            int[] validInputs = { 1, 2, 3 };
            int choice = Utils.getChoice("""
                    What would you like to do?\n1) Attack\n2) Use Item\n3) Run""",
                    validInputs, scanner);
            int dmg;

            // do damage if damage is selected, use item if that's selected, or attempt to
            // run if that's selected
            switch (choice) {
                // attack logic
                case 1:
                    dmg = player.attack();
                    System.out.println(player.name + " dealt " + dmg + " to "
                            + opponent.name + ".");
                    opponent.takeDamage(dmg);
                    System.out.println(opponent.name + " is on " + opponent.health
                            + " hit points.");
                    break;
                // use item logic
                case 2:
                    // get list of combat items available to be used by the player
                    String[] availableItems = Utils.getItemIntersections(player.items, usableItems);
                    // break out if no items are available
                    if (availableItems[0] == null) {
                        System.out.println("You have no items available to use.");
                        break;
                    }

                    // prompt for which items to use
                    String output = "Which item do you want to use?\n";
                    // tale all items in availableItems and map them to a possible choice
                    int[] itemInputs = new int[availableItems.length];
                    for (int i = 0; i < availableItems.length; i++) {
                        output += i + ") " + availableItems[i] + "\n";
                        itemInputs[i] = i;
                    }

                    // get player choice based on available items
                    int itemChoice = Utils.getChoice(output, itemInputs, scanner);

                    // big if statement to do different things based on what item the player picked
                    if (availableItems[itemChoice] == "potion") {
                        System.out.println("You use the potion.");
                        player.heal(rand.nextInt(1, 5));
                        player.items.replace("potion", false);
                    }
                    break;
                // run logic
                case 3:
                    if (rand.nextInt(0, 5) >= 1) {
                        System.out.println("You successfully escaped.");
                        return;
                    } else {
                        System.out.println("You were unable to escape");
                    }
                    break;

                default:
                    break;
            }

            // test if player is dead early
            if (player.health == 0) {
                break;
            }

            // do enemy turn, will always attack because the enemy is dumb
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

        Character player = new Character(name, createPlayerItems(), "a1", 10, 10, 1, 4);
        boolean play = true;

        // Introduction text.

        // Main game loop.
        while (play) {
            switch (player.location) {
                // Hall Room
                case "a1":
                    // first check if the player has the flashlight or not
                    if (!player.items.get("flashlight")) {
                        // room text
                        System.out.println("""
                                A dim hall with rusty walls.
                                A flashlight sits on a table.""");
                        // initialise input and give player options
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                To both the east and south are doors, the eastern one wooden while
                                the southern one iron.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.
                                3) Pick up the flashlight.
                                """, validInputs, scanner);

                        // do different things depending on choice
                        if (choice == 3) {
                            player.items.replace("flashlight", true);
                        } else if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println("The door is stuck.");
                        }
                    } else {
                        // if player has picked up the flashlight
                        System.out.println("A dim hall with rusty walls.");
                        // don't give player option to pick up flashlight again
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                To both the north and south are doors, the northern one wooden while
                                the southern one iron.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println("The door is stuck.");
                        }
                    }
                    break;

                // Storage Room
                case "b1":
                    if (!player.items.get("crowbar")) {
                        System.out.println("A messy room with shelves. A crowbar is by a crate.");

                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                To both the south and west are doors, and a crowbar sits by a
                                crate in the middle of the room.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                3) Pick up the crowbar.
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("crowbar", true);
                            System.out.println("You picked up the crowbar.");
                        } else if (choice == 1) {
                            player.location = "a1";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "b2";
                            System.out.println("You go through the south door.");
                        }
                    } else {
                        System.out.println("A messy room with shelves.");

                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                To both the south and west are doors.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "a1";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "b2";
                            System.out.println("You go through the south door.");
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
