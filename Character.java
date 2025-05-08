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
    // Character's current and maximum possible health.
    int health;
    int maxHealth;

    /**
     * Character constructor to initialize the class with all parameters filled by
     * an input.
     */
    public Character(String nameInput, HashMap<String, Boolean> itemsInput, String locationInput,
            int healthInput, int maxHealthInput) {
        name = nameInput;
        items = itemsInput;
        location = locationInput;
        health = healthInput;
        maxHealth = maxHealthInput;
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
        health += amount;
        health = Math.clamp(health, 0, maxHealth);
    }
}
