import java.util.HashMap;

/**
 * Character.
 *
 * A class to contain all of the data for the player character, might be useful
 * to also act as a class or base class for NPCs.
 *
 * @author Mei Waterman (indiebreath)
 */
public class Character {
    // The character name.
    String name;
    // A hashmap containing all of the items and a boolean to determine whether or
    // not the player has it in their inventory.
    HashMap<String, Boolean> items;
    // a1 is top-left, e5 is bottom-right. Letters are horizontal, numbers are
    // vertical.
    String location;

    /**
     * Character constructor to initialize the class with various parameters such as
     * location and items.
     */
    public Character() {
        items = new HashMap<String, Boolean>();
        items.put("lantern", false);
        items.put("oil", false);
        items.put("lighter", false);

        location = "c3";
    }

    /**
     * Method to set the character's name using a given input.
     *
     * @param input what the character's name should be set to.
     */
    public void setName(String input) {
        name = input;
    }
}
