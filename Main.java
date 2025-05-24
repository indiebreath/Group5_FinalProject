import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Main.
 *
 * @author Mei Waterman (indiebreath)
 */
public class Main {
    static String accessCode = "";

    static HashMap<String, Boolean> createPlayerItems() {
        HashMap<String, Boolean> items = new HashMap<String, Boolean>();
        items.put("flashlight", false);
        items.put("crowbar", false);
        items.put("battery", false);
        items.put("paper", false);
        items.put("medkit", false);
        items.put("toolbox", false);
        items.put("hazmat suit", false);
        items.put("radio", false);

        return items;
    }

    /**
     * A method to return different display texts based off which item it is given.
     * Used to act as an inspect option for items.
     * 
     * @param item the item to inspect
     * @return the resulting text to return based on the item
     */
    static String useItem(String item) {
        String result = "";
        if (item == "paper") {
            result = "The paper has a code on it: " + accessCode;
        } else if (item == "flashlight") {
            result = "A flashlight. The bulb seems okay, but it needs a battery to work.";
        } else if (item == "crowbar") {
            result = """
                    A crowbar. Useful in prying things open, or could work as a bludgeoning weapon.
                    """;
        } else if (item == "battery") {
            result = "A battery. Can be used with a flashlight to give it charge.";
        } else if (item == "medkit") {
            result = "A stocked medkit. Useful when one is injured.";
        } else if (item == "toolbox") {
            result = "A toolbox. It contains everything you might need to fix small items.";
        } else if (item == "hazmat suit") {
            result = "A hazmat suit. Can be used to protect yourself from environmental hazards";
        } else if (item == "radio") {
            result = "A radio. Can be used to call for help, if you have the access code.";
        }

        return result;
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
        boolean npcHealed = false;
        accessCode = Utils.generateAlphanumericString(7);

        // Introduction text.

        // Main game loop.
        while (play) {
            switch (player.location) {
                // Hall Room
                case "a1":
                    // first check if the player has the flashlight or not
                    if (!player.items.get("flashlight") && !player.items.get("battery")) {
                        // initialise input and give player options
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                The room is so dark you can barely see from one end to the other.
                                You can barely make out a table which appears to have a flashlight
                                on it. Additionally, there are doors to the south and east, though
                                the southern door looks quite damaged.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.
                                3) Pick up the flashlight.

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("flashlight") && !player.items.get("battery")) {
                        // if player has picked up the flashlight don't give player option to pick
                        // up flashlight again
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                The room is so dark you can barely see from one end to the other.
                                You can barely make out the table you took the flashlight from, as
                                well as doors leading to the south and east, with the southern one
                                looking quite damaged.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println(
                                    "You attempt to open the southern door, but it doesn't budge.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (player.items.get("flashlight") && player.items.get("battery")) {
                        // if player has picked up the flashlight and the battery
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                With the charged flashlight you are able to see clearly. The room's
                                walls are rust and damaged, as is the table you took the flashlight
                                from and the two doors, one which leads south and appears quite
                                damaged, and another that leads east.
                                What do you do?
                                1) Attempt the east door.
                                2) Attempt the south door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b1";
                            System.out.println("You walk through the east door.");
                        } else if (choice == 2) {
                            System.out.println(
                                    "You attempt to open the southern door, but it doesn't budge.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }

                    }
                    break;

                // Storage Room
                case "b1":
                    if (!player.items.get("crowbar")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // if player does not have crowbar, and doesn't have both the flashlight
                        // and battery
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                Through the darkness you can barely make out a series of shelves
                                and crates, all of which look messy and disorganised, as if they
                                had been hastily looted. By one of the crates you notice a crowbar,
                                and there are doors leading to the west and south.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                3) Pick up the crowbar.

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("crowbar")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // if player has picked up the crowbar, but doesn't have both the flashlight
                        // and battery
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Through the darkness you can barely make out a series of shelves
                                and crates, all of which look messy and disorganised, as if they
                                had been hastily looted. There are doors to the west and south
                                leading out of the room.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "a1";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "b2";
                            System.out.println("You go through the south door.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (player.items.get("crowbar")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if the player has both the crowbar, and does have both the flashlight
                        // and battery
                        int[] validInputs = { 1, 2, 3 };
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
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    }
                    break;

                // Control Room
                case "b2":
                    if (!player.items.get("battery") && player.items.get("flashlight")) {
                        // if the player has the flashlight but not the battery
                        int[] validInputs = { 1, 2, 3, 4, 5 };
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

                                5) List inventory
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
                        } else if (choice == 5) {
                            player.listInventory();
                        }
                    } else if (!player.items.get("battery") && !player.items.get("flashlight")) {
                        // if the player doesn't have either the battery or the flashlight
                        int[] validInputs = { 1, 2, 3, 4, 5 };
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

                                5) List inventory
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
                        } else if (choice == 5) {
                            player.listInventory();
                        }
                    } else if (player.items.get("battery") && !player.items.get("flashlight")) {
                        // if the player has the battery but not the flashlight
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

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("battery") && player.items.get("flashlight")
                            && !player.items.get("paper")) {
                        // if the player has both the battery and flashlight, but not the paper
                        int[] validInputs = { 1, 2, 3, 4, 5 };
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

                                5) List inventory
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
                        } else if (choice == 5) {
                            player.listInventory();
                        }
                    } else if (player.items.get("battery") && player.items.get("flashlight")
                            && player.items.get("paper")) {
                        // if the player has picked up the battery, flashlight, and paper
                        int[] validInputs = { 1, 2, 3, 4 };
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

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    }
                    break;

                // Bunk Room
                case "c2":
                    if (!player.items.get("medkit")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // if player doesn't have the medkit, and hasn't picked up the flashlight
                        // and the battery
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Dark silhouettes of bunk beds fill the room, leading off into
                                almost complete darkness at the eastern wall. There are doors
                                to the west and south, and there doesn't appear to be anything
                                in the room.
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
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (!player.items.get("medkit")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if the player doesn't have the medkit, but has both the battery
                        // and flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                Flashlight in hand, you are able to see a series of bunk beds
                                leading off to the east. There appears to be enough to house
                                twenty people. In the light, you notice a medkit underneath one
                                of the pillows. Additionally, there are doors to the west and south.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.
                                3) Pick up the medkit

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("medkit")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if the player has the medkit, and has the flashlight and battery
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Flashlight in hand, you are able to see a series of bunk beds
                                leading off to the east. There appears to be enough to house
                                twenty people. There are doors to the west and south.
                                What do you do?
                                1) Go through the west door.
                                2) Go through the south door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the west door.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the south door.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    }
                    break;

                // Shaft room
                case "b3":
                    if (!npcInteracted) {
                        // if player hasn't interacted with the survivor
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                You come into a tight shaft with hanging wires, some of
                                which spark with electricity. Inside you see a dishevelled
                                man, mumbling about something, though you can't quite make
                                out what he's saying. There are exits to the north and east.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the east exit.
                                3) Talk to the survivor.

                                4) list inventory
                                """, validInputs, scanner);

                        if (choice == 3) {
                            npcInteracted = true;
                            System.out.println("""
                                    The man looks up at you and speaks. \"Do you have a
                                    medkit? I think I broke my leg when I stumbled in here.\"
                                    """);
                        } else if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the east exit.");
                        }
                    } else if (npcInteracted && player.items.get("medkit")) {
                        // if the player has interacted with the survivor and does have the medkit
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                You come into a tight shaft with hanging wires, some of
                                which spark with electricity. Inside you see a dishevelled
                                man, who looked up at you hopefully. There are exits to the
                                north and east.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the east exit.
                                3) Give the man the medkit.

                                4) List inventory
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("medkit", false);
                            player.items.replace("hazmat suit", true);
                            npcHealed = true;
                            System.out.println("""
                                    The man says to you, \"Thank you so much. Here, take this
                                    hazmat suit. I don't think I'll be needing it while I'm in
                                    here. Also, you should know that there's a radio in the
                                    armoury.\"""");
                        } else if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the east exit.");
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (npcInteracted && (!player.items.get("medkit") && !npcHealed)) {
                        // if the player has interacted with the survivor, but doesn't have
                        // the medkit
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You come into a tight shaft with hanging wires, some of
                                which spark with electricity. Inside you see a dishevelled
                                man, who looks up at you hopefully. There are exits to the
                                north and east.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the east exit.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the east exit.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (npcInteracted && npcHealed) {
                        // if the player has both interacted with and healed the npc
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You come into a tight shaft with hanging wires, some of
                                which spark with electricity. Inside you see a dishevelled
                                man, who is resting an injured leg. There are exits to the
                                north and east.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the east exit.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "b2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "c3";
                            System.out.println("You go through the east exit.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    }
                    break;

                // Kitchen Room
                case "c3":
                    if (!player.items.get("toolbox") && !player.items.get("radio")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // if player doesn't have toolbox, radio, flashlight, or battery
                        int[] validInputs = { 1, 2, 3, 4, 5 };
                        int choice = Utils.getChoice("""
                                Cookware clatters around your feet as you stumble your way
                                through the dark kitchen. Running your hand across the
                                counter to help you navigate, it brushes against a toolbox.
                                You are also able to barely see doors to the north, west,
                                and south.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the west exit.
                                3) Go through the south exit.
                                4) Pick up the toolkit.

                                5) List inventory
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("toolbox", true);
                            System.out.println("You pick up the toolbox.");
                        } else if (choice == 1) {
                            player.location = "c2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "b3";
                            System.out.println("You go through the west exit.");
                        } else if (choice == 3) {
                            player.location = "c4";
                            System.out.println("You go through the south exit.");
                        } else if (choice == 5) {
                            player.listInventory();
                        }
                    } else if (!player.items.get("toolbox") && player.items.get("radio")
                            && (!player.items.get("flashlight") && !player.items.get("batter"))) {
                        // if player doesn't have the toolbox, does have the radio, and doesn't
                        // have a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4, 5 };
                        int choice = Utils.getChoice("""
                                Cookware clatters around your feet as you stumble your way
                                through the dark kitchen. Running your hand across the
                                counter to help you navigate, it brushes against a toolbox.
                                You are also able to barely see doors to the north, west,
                                and south.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the west exit.
                                3) Go through the south exit.
                                4) Pick up the toolkit.

                                5) List inventory
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("toolbox", true);
                            System.out.println(
                                    "You pick up the toolbox and use it to fix the radio.");
                        } else if (choice == 1) {
                            player.location = "c2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "b3";
                            System.out.println("You go through the west exit.");
                        } else if (choice == 3) {
                            player.location = "c4";
                            System.out.println("You go through the south exit.");
                        } else if (choice == 5) {
                            player.listInventory();
                        }

                    } else if (player.items.get("toolbox")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // if player has picked up the toolbox, but hasn't picked up the radio,
                        // or has a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                Cookware clatters around your feet as you stumble your way
                                through the dark kitchen. You run your hand across the
                                counter to help you navigate and are able to barely see
                                doors to the north, west, and south.
                                What do you do?
                                1) Go through the north exit.
                                2) Go through the west exit.
                                3) Go through the south exit.

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (!player.items.get("toolbox") && !player.items.get("radio")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if player doesn't have the toolbox, doesn't have the radio, but does
                        // have a charged bettery
                        int[] validInputs = { 1, 2, 3, 4, 5 };
                        int choice = Utils.getChoice("""
                                You come into a cluttered kitchen. Pots and pans are strewn about
                                and there is some rotting food on the stoves. As you walk through
                                you notice a toolbox sitting on one of the counters, and doors to
                                the north, west, and south.
                                What do you do?
                                1) Go through the northern exit.
                                2) Go through the western exit.
                                3) Go through the southern exit.
                                4) Pick up the toolbox.

                                5) List inventory
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("toolbox", true);
                            System.out.println("You pick up the toolbox");
                        } else if (choice == 1) {
                            player.location = "c2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "b3";
                            System.out.println("You go through the west exit.");
                        } else if (choice == 3) {
                            player.location = "c4";
                            System.out.println("You go through the south exit.");
                        } else if (choice == 5) {
                            player.listInventory();
                        }
                    } else if (!player.items.get("toolbox") && player.items.get("radio")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if player doesn't have the toolbox, but does have the radio, and does
                        // have a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4, 5 };
                        int choice = Utils.getChoice("""
                                You come into a cluttered kitchen. Pots and pans are strewn about
                                and there is some rotting food on the stoves. As you walk through
                                you notice a toolbox sitting on one of the counters, and doors to
                                the north, west, and south.
                                What do you do?
                                1) Go through the northern exit.
                                2) Go through the western exit.
                                3) Go through the southern exit.
                                4) Pick up the toolbox.

                                5) List inventory
                                """, validInputs, scanner);

                        if (choice == 4) {
                            player.items.replace("toolbox", true);
                            System.out.println(
                                    "You pick up the toolbox and use it to fix the radio.");
                        } else if (choice == 1) {
                            player.location = "c2";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            player.location = "b3";
                            System.out.println("You go through the west exit.");
                        } else if (choice == 3) {
                            player.location = "c4";
                            System.out.println("You go through the south exit.");
                        } else if (choice == 5) {
                            player.listInventory();
                        }
                    } else if (player.items.get("toolbox")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if player has the toolbox and a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                You come into a cluttered kitchen. Pots and pans are strewn about
                                and there is some rotting food on the stoves. There are doors to
                                the north, west, and south.
                                What do you do?
                                1) Go through the northern exit.
                                2) Go through the western exit.
                                3) Go through the southern exit.

                                4) List inventory
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
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    }
                    break;

                // Hazard Room
                case "c4":
                    if (!player.items.get("hazmat")) {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You enter a room with a flickering illuminating it. There are
                                radiation signs encompassing the eastern part of the room, with
                                a hallway past them. Another door is to the north, with no warning
                                signs in it's direction.
                                What do you do?
                                1) Go through the north exit.
                                2) Attempt to pass through the radiation.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c3";
                            System.out.println("You go through the north exit.");
                        } else if (choice == 2) {
                            System.out.println("""
                                    You attempt to brave the radiation.
                                    As you walk you quickly succumb to it, and die.""");
                            play = false;
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else {
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You enter a room with a flickering illuminating it. There are
                                radiation signs encompassing the eastern part of the room, with
                                a hallway past them. Another door is to the north, with no warning
                                signs in it's direction.
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
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    }
                    break;

                // Armoury room
                case "d4":
                    if (!player.items.get("crowbar")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // if player doesn't have the crowbar, and doesn't have a charged flashlight
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You can't make out much of the room in the darkness, but you can
                                make out the shapes of lockers covering the walls, some of them
                                fully enclosed and others that you can barely see into. In one of
                                them you notice the radio that the survivor was talking about, but
                                the locker it's in is locked. There are doors to the east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (player.items.get("crowbar") && !player.items.get("radio")
                            && !player.items.get("toolbox")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // player does have the crowbar, but doesn't have the radio, doesn't have
                        // the toolbox, and doesn't have a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                You can't make out much of the room in the darkness, but you can
                                make out the shapes of lockers covering the walls, some of them
                                fully enclosed and others that you can barely see into. In one of
                                them you notice the radio that the survivor was talking about, but
                                the locker it's in is locked. There are doors to the east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                3) Use the crowbar to pry open the locker.

                                4) List inventory
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("radio", true);
                            System.out.println("""
                                    You use the crowbar to pry open the locker. It takes some
                                    effort, but you eventually get it open and take the radio
                                    inside. Looking it over it appears to be broken, but seems
                                    fixable with some tools.""");
                        } else if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("crowbar") && !player.items.get("radio")
                            && !player.items.get("toolbox")
                            && (!player.items.get("flashlight") && !player.items.get("battery"))) {
                        // player does have the crowbar, but doesn't have the radio, does have
                        // the toolbox, and doesn't have a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                You can't make out much of the room in the darkness, but you can
                                make out the shapes of lockers covering the walls, some of them
                                fully enclosed and others that you can barely see into. In one of
                                them you notice the radio that the survivor was talking about, but
                                the locker it's in is locked. There are doors to the east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                3) Use the crowbar to pry open the locker.

                                4) List inventory
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("radio", true);
                            System.out.println("""
                                    You use the crowbar to pry open the locker. It takes some
                                    effort, but you eventually get it open and take the radio
                                    inside. Looking it over it appears to be broken, but you
                                    easily fix it with your toolbox.""");
                        } else if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("radio")
                            && (!player.items.get("flashlight") && player.items.get("battery"))) {
                        // player has the radio, but not a charged flashlight
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You can't make out much of the room in the darkness, but you can
                                make out the shapes of lockers covering the walls, some of them
                                fully enclosed and others that you can barely see into. There are
                                doors to the east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (!player.items.get("crowbar")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // if player doesn't have the crowbar, but does have a charged flashlight
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Thanks to your flashlight you can see that the room is some kind
                                of armour. Locker fill the room, some that are fully enclosed, and
                                others that aren't, though almost all of them have been looted. In
                                one of them you notice the radio that the survivor was talking
                                about, but the locker it's in is locked. There are doors to the
                                east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (player.items.get("crowbar") && !player.items.get("radio")
                            && !player.items.get("toolbox")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // player does have the crowbar, but doesn't have the radio, doesn't have
                        // the toolbox, and does have a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                Thanks to your flashlight you can see that the room is some kind
                                of armour. Locker fill the room, some that are fully enclosed, and
                                others that aren't, though almost all of them have been looted. In
                                one of them you notice the radio that the survivor was talking
                                about, but the locker it's in is locked. There are doors to the
                                east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                3) Use the crowbar to pry open the locker.

                                4) List inventory
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("radio", true);
                            System.out.println("""
                                    You use the crowbar to pry open the locker. It takes some
                                    effort, but you eventually get it open and take the radio
                                    inside. Looking it over it appears to be broken, but seems
                                    fixable with some tools.""");
                        } else if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("crowbar") && !player.items.get("radio")
                            && player.items.get("toolbox")
                            && (player.items.get("flashlight") && player.items.get("battery"))) {
                        // player does have the crowbar, but doesn't have the radio, does have
                        // the toolbox, and does have a charged flashlight
                        int[] validInputs = { 1, 2, 3, 4 };
                        int choice = Utils.getChoice("""
                                Thanks to your flashlight you can see that the room is some kind
                                of armour. Locker fill the room, some that are fully enclosed, and
                                others that aren't, though almost all of them have been looted. In
                                one of them you notice the radio that the survivor was talking
                                about, but the locker it's in is locked. There are doors to the
                                east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.
                                3) Use the crowbar to pry open the locker.

                                4) List inventory
                                """, validInputs, scanner);

                        if (choice == 3) {
                            player.items.replace("radio", true);
                            System.out.println("""
                                    You use the crowbar to pry open the locker. It takes some
                                    effort, but you eventually get it open and take the radio
                                    inside. Looking it over it appears to be broken, but you
                                    easily fix it with your toolbox.""");
                        } else if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "d5";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 4) {
                            player.listInventory();
                        }
                    } else if (player.items.get("radio")
                            && (!player.items.get("flashlight") && player.items.get("battery"))) {
                        // player has the radio, and a charged flashlight
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                Thanks to your flashlight you can see that the room is some kind
                                of armour. Locker fill the room, some that are fully enclosed, and
                                others that aren't, though almost all of them have been looted.
                                There are doors to the east and west.
                                What do you do?
                                1) Go through the western door.
                                2) Go through the eastern door.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "c4";
                            System.out.println("You go through the western door.");
                        } else if (choice == 2) {
                            player.location = "e4";
                            System.out.println("You go through the eastern door.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    }
                    break;

                // Exit room
                case "e4":
                    if (!player.items.get("radio")) {
                        // player doesn't have the radio
                        int[] validInputs = { 1, 2 };
                        int choice = Utils.getChoice("""
                                You come into a communication room lit by some lights. There is a
                                console with various inputs, one of which is labelled 'radio'.
                                There is only one exit to the room, which is to the west.
                                What do you do?
                                1) Go through the western exit.

                                2) List inventory
                                """, validInputs, scanner);

                        if (choice == 1) {
                            player.location = "d4";
                            System.out.println("You go through the western exit.");
                        } else if (choice == 2) {
                            player.listInventory();
                        }
                    } else if (player.items.get("radio") && !player.items.get("toolbox")) {
                        // player has the radio but not the toolbox
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You come into a communication room lit by some lights. There is a
                                console with various inputs, one of which is labelled 'radio',
                                which you think refers to the radio you found in the armoury. There
                                is only on exit to the room, which is to the west.
                                What do you do?
                                1) Go through the western exit.
                                2) Plug the radio into the console.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 2) {
                            System.out.println("""
                                    You plug the radio into the console, but nothing happens. It
                                    appears that the radio is too broken to work, but does look
                                    like it can be fixed with some tools.""");
                        } else if (choice == 1) {
                            player.location = "e4";
                            System.out.println("You go through the western exit.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    } else if (player.items.get("radio") && player.items.get("toolbox")) {
                        // player has the radio and the toolbox
                        int[] validInputs = { 1, 2, 3 };
                        int choice = Utils.getChoice("""
                                You come into a communication room lit by some lights. There is a
                                console with various inputs, one of which is labelled 'radio',
                                which you think refers to the radio you found in the armoury. There
                                is only on exit to the room, which is to the west.
                                What do you do?
                                1) Go through the western exit.
                                2) Plug the radio into the console.

                                3) List inventory
                                """, validInputs, scanner);

                        if (choice == 2) {
                            System.out.println("""
                                    You plug the radio into the console, which whirs to life and
                                    begins interfacing with the radio. After a few seconds the
                                    display changes and the text \"Please enter access code.\"
                                    appears on the screen.""");

                            System.out.println("Enter code:");
                            String codeInput = scanner.nextLine();

                            if (codeInput == accessCode) {
                                System.out.println("""
                                        The console accepts the code and the radio comes to life
                                        with static. After a few seconds the static is replaced by
                                        rythmic beeping, a morse code message asking for help.""");
                                System.out.println("Congrats, you won by calling for help.");
                                play = false;
                            } else {
                                System.out.println("The console rejects the code.");
                            }
                        } else if (choice == 1) {
                            player.location = "e4";
                            System.out.println("You go through the western exit.");
                        } else if (choice == 3) {
                            player.listInventory();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
