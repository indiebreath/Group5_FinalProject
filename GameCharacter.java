import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Character.
 *
 * <p>
 * A class to contain all of the data for the player character, might be useful
 * to also act as a class or base class for NPCs.
 * </p>
 *
 * @author Mei Waterman (indiebreath)
 */
public class GameCharacter {
    // The character name.
    String name;
    // A hashmap containing all of the items and a boolean to determine whether or
    // not the player has it in their inventory.
    HashMap<String, Boolean> items;
    // a1 is top-left, e5 is bottom-right. Letters are horizontal, numbers are
    // vertical.
    String location;
    // Character's current and maximum possible health.
    int health;
    int maxHealth;
    // Character's default damage value;
    int minDamage;
    int maxDamage;

    /**
     * The constructor for the GameCharacter class.
     *
     * @param nameInput      what to set the character's name to, as a String
     * @param itemsInput     what to set the character's inventory to, as a HashMap
     *                       of Strings and Booleans
     * @param locationInput  what to set the character's location to, as a String
     * @param healthInput    what to set the character's health to, as an int
     * @param maxHealthInput what to set the character's maximum health to, as an
     *                       int
     * @param minDamageInput what to set the character's minimum damage to, as an
     *                       int
     * @param maxDamageInput what to set the character's maximum damage to, as an
     *                       int
     */
    public GameCharacter(String nameInput, HashMap<String, Boolean> itemsInput,
            String locationInput, int healthInput, int maxHealthInput, int minDamageInput,
            int maxDamageInput) {
        name = nameInput;
        items = itemsInput;
        location = locationInput;
        health = healthInput;
        maxHealth = maxHealthInput;
        minDamage = minDamageInput;
        maxDamage = maxDamageInput;
    }

    /**
     * Method to take damage, lowering the character's health by the amount of
     * damage taken, also ensure that health does not go below 0.
     *
     * @param damage the amount of damage taken, and the amount to reduce health by.
     */
    public void takeDamage(int damage) {
        health -= damage;
        health = Math.clamp(health, 0, maxHealth);
    }

    /**
     * Method to heal health by a given amount, does not go over maximum health.
     *
     * @param amount the amount to heal, if this plus health is above maxHealth then
     *               the remainder will be wasted.
     */
    public void heal(int amount) {
        System.out.println("You healed for " + amount + " hit points.");
        health += amount;
        health = Math.clamp(health, 0, maxHealth);
    }

    /**
     * Method to make an attack roll. Very basic as it only checks for the existence
     * of the sword (working name) in the character's inventory, then deals a range
     * of damage based on whether or not that item is in the character's inventory.
     * Can be reworked with layered if statements to automatically pick highest
     * damage dealing item if multiple weapons are implemented though.
     *
     * @return the amount of damage dealt to the opponent.
     */
    public int attack() {
        Random rand = new Random();

        // long equals statement is to have it return false if the checked value is null
        if (Boolean.TRUE.equals(items.get("sword"))) {
            return rand.nextInt(4, 7);
        } else {
            return rand.nextInt(minDamage, maxDamage);
        }
    }

    /**
     * Method to get all collected items in the Character's inventory and displays
     * the string to the output.
     */
    public void listInventory() {
        String inventory = "You have:\n";

        for (String x : items.keySet()) {
            if (items.get(x)) {
                inventory += " - " + x + "\n";
            }
        }

        System.out.println(inventory);
    }

    /**
     * Method to get a list of all collected items in the Character's inventory then
     * return it as an array of strings.
     *
     * @return an array of items in the Character's inventory that are
     *         true/collected
     */
    public String[] getInventory() {
        ArrayList<String> inventory = new ArrayList<String>();

        int index = 0;
        for (String x : items.keySet()) {
            if (items.get(x)) {
                inventory.add(x);
            }
            index++;
        }

        index = 0;
        String[] output = new String[inventory.size()];
        for (String x : inventory) {
            output[index] = x;
            index++;
        }

        return output;
    }
}
