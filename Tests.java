/**
 * Tests
 *
 * A class to Test various methods within the greater program without needing to
 * edit the main files.
 *
 * @author Mei Waterman (indiebreath)
 */
public class Tests {
    public static void main(String[] args) {
        if (args[0].equals("0")) {
            testDamage();
        } else if (args[0].equals("1")) {
            testHealing();
        }
    }

    /**
     * Method to test the takeDamage function within the Chracter class, ensuring
     * that damage is taken correctly and is correctly clamped with 0 being the
     * lowest value.
     */
    static void testDamage() {
        Character testCharacter = new Character();

        System.out.println("--- Testing Character Damage ---");
        System.out.println("TestCharacter's health: " + testCharacter.health);
        System.out.println("Dealing 5 damage to TestCharacter.");
        System.out.println("Expected Outcome: TestCharacter's health will be 5.");

        testCharacter.takeDamage(5);
        System.out.println("TestCharacter's health: " + testCharacter.health);

        if (testCharacter.health == 5) {
            System.out.println("Test Passed.");
        } else {
            System.out.println("Test Failed.");
        }

        System.out.println("Dealing 6 damage to Test Character.");
        System.out.println("Expected outcome: TestCharacter's health will be lowered to -1, then clamped to 0.");

        testCharacter.takeDamage(6);
        System.out.println("TestCharacter's health: " + testCharacter.health);

        if (testCharacter.health == 0) {
            System.out.println("Test Passed.");
        } else {
            System.out.println("Test Failed.");
        }
    }

    /**
     * Method to test the heal function within the Character class, ensuring that
     * health is properly restored, and that it is properly clamped to with
     * maxHealth as the maximum value.
     */
    static void testHealing() {
        Character testCharacter = new Character();

        System.out.println("--- Testing Character Healing ---");
        System.out.println("TestCharacter's health: " + testCharacter.health);
        System.out.println("Dealing 5 damage to TestCharacter to prepare for test.");
        testCharacter.takeDamage(5);
        System.out.println("TestCharacter's health: " + testCharacter.health);

        System.out.println("Healing TestCharacter for 3 health.");
        System.out.println("Expected outcome: TestCharacter's health will be equal to 8.");

        testCharacter.heal(3);
        System.out.println("TestCharacter's health: " + testCharacter.health);

        if (testCharacter.health == 8) {
            System.out.println("Test passed.");
        } else {
            System.out.println("Test failed.");
        }

        System.out.println("Healing TestCharacter for 5 health.");
        System.out.println("Expected outcome: TestCharacter's health will be increased to 13, then clamped to 10.");

        testCharacter.heal(5);
        System.out.println("TestCharacter's health: " + testCharacter.health);

        if (testCharacter.health == 10) {
            System.out.println("Test passed.");
        } else {
            System.out.println("Test failed.");
        }
    }
}
