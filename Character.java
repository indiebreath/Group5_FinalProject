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
public class Character {
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
     * Character constructor to initialize the class with all parameters filled by
     * an input.
     */
    public Character(String nameInput, HashMap<String, Boolean> itemsInput, String locationInput,
            int healthInput, int maxHealthInput, int minDamageInput, int maxDamageInput) {
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
}
