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
        items.put("battery", false);
        items.put("paper", false);
        items.put("medkit", false);
        items.put("toolkit", false);
        items.put("hazmat suit", false);
        items.put("radio", false);

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
        boolean npcInteracted = false;

        // Introduction text.

        // Main game loop.
        while (play) {
            switch (player.location) {
                // Hall Room
                case "a1":
                    // first check if the player has the flashlight or not
                    if (!player.items.get("flashlight") && !player.items.get("battery")) {
                        // initialise input and give player options
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                The room is so dark you can barely see from one end to the other.
                                You can barely make out a table which appears to have a flashlight
                                on it. Additionally, there are doors to the south and east, though
                                the southern door looks quite damaged.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.
                                3) Pick up the flashlight.
                                """, validInputs, scanner);

                        // do different things depending on choice
                        if (choice == 3) {
                            player.items.replace("flashlight", true);
                            System.out.println("""
                                    You pick up the flashlight. When you attempt to turn it on it
                                    doesn't, evidently out of charge.""");
                        } else if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println(
                                    "You attempt to open the southern door, but it doesn't budge.");
                        }
                    } else if (player.items.get("flashlight") && !player.items.get("battery")) {
                        // if player has picked up the flashlight don't give player option to pick
                        // up flashlight again
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                The room is so dark you can barely see from one end to the other.
                                You can barely make out the table you took the flashlight from, as
                                well as doors leading to the south and east, with the southern one
                                looking quite damaged.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println(
                                    "You attempt to open the southern door, but it doesn't budge.");
                        }
                    } else if (player.items.get("flashlight") && player.items.get("battery")) {
                        // if player has picked up the flashlight and the battery
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                With the charged flashlight you are able to see clearly. The room's
                                walls are rust and damaged, as is the table you took the flashlight
                                from and the two doors, one which leads south and appears quite
                                damaged, and another that leads east.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println(
                                    "You attempt to open the southern door, but it doesn't budge.");
                        }

                    }
                    break;

                // Storage Room
                case "b1":
                    if (!player.items.get("crowbar")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Through the darkness you can barely make out a series of shelves
                                and crates, all of which look messy and disorganised, as if they
                                had been hastily looted. By one of the crates you notice a crowbar,
                                and there are doors leading to the west and south.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                3) Pick up the crowbar.
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("crowbar", true);
                            System.out.println("""
                                    You pick up the crowbar. It is decently heavy and looks sturdy
                                    enough to open or break things.""");
                        } else if (choice == 1) {
                            player.location = "a1";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "b2";
                            System.out.println("You go through the south door.");
                        }
                    } else if (player.items.get("crowbar")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                Through the darkness you can barely make out a series of shelves
                                and crates, all of which look messy and disorganised, as if they
                                had been hastily looted. There are doors to the west and south
                                leading out of the room.
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
                    } else if (player.items.get("crowbar")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                Using the flashlight you are able to make out more details of the
                                room. The markings on the crates are faded and scratched,
                                indicating age, and the shelving is rusty. There are two doors
                                leading out of the room, one to the west and one to the south.
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

                // Control Room
                case "b2":
                    if (!player.items.get("battery") && player.items.get("flashlight")) {
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                The room is lit by a series of flickering monitors. Some show
                                camera feeds, others show system information about the bunker,
                                while others simply show static or are blank. On a desk you
                                notice a battery, and there are doors to the north, east, and south.
                                What do you do?
                                1) Go through the north door.
                                2) Go through the east door.
                                3) Go through the south door.
                                4) Pick up the battery.
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("battery", true);
                            System.out.println("""
                                    Hoping that it's charged, you pick up the battery and put it
                                    in your flashlight, with it thankfully coming to life and
                                    illuminating the room.""");
                        } else if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You go through the north door.");
                        } else if (choice == 2) {
                            player.location = "c2";
                            System.out.println("You go through the east door.");
                        } else if (choice == 3) {
                            player.location = "b3";
                            System.out.println("You go through the south door.");
                        }
                    } else if (!player.items.get("battery") && !player.items.get("flashlight")) {
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                The room is lit by a series of flickering monitors. Some show
                                camera feeds, others show system information about the bunker,
                                while others simply show static or are blank. On a desk you
                                notice a battery, and there are doors to the north, east, and south.
                                What do you do?
                                1) Go through the north door.
                                2) Go through the east door.
                                3) Go through the south door.
                                4) Pick up the battery.
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("battery", true);
                            System.out.println("You pick up the battery, hoping that it's charged");
                        } else if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You go through the north door.");
                        } else if (choice == 2) {
                            player.location = "c2";
                            System.out.println("You go through the east door.");
                        } else if (choice == 3) {
                            player.location = "b3";
                            System.out.println("You go through the south door.");
                        }
                    } else if (player.items.get("battery") && !player.items.get("flashlight")) {
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                The room is lit by a series of flickering monitors. Some show
                                camera feeds, others show system information about the bunker,
                                while others simply show static or are blank. On one of the
                                monitors you notice a person. There are doors to the north,
                                east, and south.
                                What do you do?
                                1) Go through the north door.
                                2) Go through the east door.
                                3) Go through the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You go through the north door.");
                        } else if (choice == 2) {
                            player.location = "c2";
                            System.out.println("You go through the east door.");
                        } else if (choice == 3) {
                            player.location = "b3";
                            System.out.println("You go through the south door.");
                        }
                    } else if (player.items.get("battery") && player.items.get("flashlight")
                            && !player.items.get("paper")) {
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                One wall of this room is covered in a series of flickering
                                monitors, some showing camera feeds, others showing system
                                information, and others just showing static or blank screens.
                                One of the monitors shows a person. With the light of your
                                flashlight, you notice a piece of paper on the desk under the
                                monitors. Additionally, there are doors to the north, east,
                                and south.
                                What do you do?
                                1) Go through the north door.
                                2) Go through the east door.
                                3) Go through the south door.
                                4) Pick up the piece of paper.
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("paper", true);
                            System.out.println("""
                                    You pick up the piece of paper. There is a code written on it.
                                    """);
                        } else if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You go through the north door.");
                        } else if (choice == 2) {
                            player.location = "c2";
                            System.out.println("You go through the east door.");
                        } else if (choice == 3) {
                            player.location = "b3";
                            System.out.println("You go through the south door.");
                        }
                    } else if (player.items.get("battery") && player.items.get("flashlight")
                            && player.items.get("paper")) {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                One wall of this room is covered in a series of flickering
                                monitors, some showing camera feeds, others showing system
                                information, and others just showing static or blank screens.
                                One of the monitors shows a person. Additionally, there are
                                doors to the north, east, and south.
                                What do you do?
                                1) Go through the north door.
                                2) Go through the east door.
                                3) Go through the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You go through the north door.");
                        } else if (choice == 2) {
                            player.location = "c2";
                            System.out.println("You go through the east door.");
                        } else if (choice == 3) {
                            player.location = "b3";
                            System.out.println("You go through the south door.");
                        }
                    }
                    break;

                // Bunk Room
                case "c2":
                    if (!player.items.get("medkit")) {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Old bunk beds, empty. A medkit is under a pillow.
                                To the west and south are doors.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                3) Pick up the medkit.
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("medkit", true);
                            System.out.println("You pick up the medkit.");
                        } else if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the south door.");
                        }
                    } else {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                Old bunk beds, empty. To the west and south are doors.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the south door.");
                        }
                    }
                    break;

                // Shaft room
                case "b3":
                    if (!npcInteracted) {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                A tight shaft with wires. A survivor mutters about a radio.
                                There are exits to the north and east.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the east exit.
                                3) Talk to the survivor.
                                """, validInputs, scanner);

                        if (choice == 3) {
                            npcInteracted = true;
                            System.out.println("""
                                    The survivor mutters to you.
                                    \"Radio's in the armory, need to break a crate.\"""");
                        } else if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the east exit.");
                        }
                    } else {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                A tight shaft with wires. A survivor is quiet, done talking.
                                There are exits to the north and east.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the east exit.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the east exit.");
                        }
                    }
                    break;

                // Kitchen Room
                case "c3":
                    if (player.items.get("toolkit")) {
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                A messy dining area. A toolkit is on a counter.
                                There are exits to the north, west, and south.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the west exit.
                                3) Go through the south exit.
                                4) Pick up the toolkit.
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("tookit", true);
                            System.out.println("You pick up the toolkit.");
                        } else if (choice == 1) {
                            player.location = "c2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "b3";
                            System.out.println("You go through the west exit.");
                        } else if (choice == 3) {
                            player.location = "c4";
                            System.out.println("You go through the south exit.");
                        }
                    } else {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                A messy dining area.
                                There are exits to the north, west, and south.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the west exit.
                                3) Go through the south exit.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "b3";
                            System.out.println("You go through the west exit.");
                        } else if (choice == 3) {
                            player.location = "c4";
                            System.out.println("You go through the south exit.");
                        }
                    }
                    break;

                // Hazard Room
                case "c4":
                    if (!player.items.get("hazmat")) {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                Signs warn of radiation in the area.
                                There is an exit to the north and east through the radiation.
                                What do you do?
                                1) Go through the north exit.
                                2) Attempt to pass through the radiation.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c3";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            System.out.println("""
                                    You attempt to brave the radiation.
                                    As you walk you quickly succumb to it, and die.""");
                            play = false;
                        }
                    } else {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                Signs warn of radiation in the area.
                                There is an exit to the north and east through the radiation.
                                What do you do?
                                1) Go through the north exit.
                                2) Use the hazmat suit to pass through the radiation.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c3";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "d4";
                            System.out.println(
                                    "With the hazmat suit you easily pass through the radiation.");
                        }
                    }
                    break;

                // Armoury room
                case "d4":
                    if (!player.items.get("crowbar") && !player.items.get("radio")) {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                A locked room with racks. A radio is in a crate, which you need
                                some way to open.
                                There are doors to the west and east.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        }
                    } else if (player.items.get("crowbar") && !player.items.get("radio")) {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                A locked room with racks. A radio is in a crate, which you can
                                open with the crowbar.
                                There are doors to the west and east.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                3) Break open the crate.
                                """, validInputs, scanner);

                        if (choice == 3) {
                            System.out.println("You break open the crate and pick up the radio.");
                        } else if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        }
                    } else if (player.items.get("radio")) {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                A locked room with racks.
                                There are doors to the west and east.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "e4";
                            System.out.println("You go through the eastern door.");
                        }
                    }
                    break;

                // Exit room
                case "e5":
                    if (!player.items.get("radio")) {
                        int[] validInputs = { 1 };
                        int choice = Utils.getChoice("""
                                A communication station with an antenna. You think you can use
                                a radio to call for help.
                                There is an exit to the north.
                                What do you do?
                                1) Go through the northern exit.
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "e4";
                            System.out.println("You go through the northern exit.");
                        }
                    } else {
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                A communication station with an antenna. You can use
                                the radio to call for help.
                                There is an exit to the north.
                                What do you do?
                                1) Go through the northern exit.
                                """, validInputs, scanner);

                        if (choice == 2) {
                            System.out.println("You called for help. You win!");
                            play = false;
                        } else if (choice == 1) {
                            player.location = "e4";
                            System.out.println("You go through the northern exit.");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
