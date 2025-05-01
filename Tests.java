public class Tests {
    public static void main(String[] args) {
        if (args[0].equals("0")) {
            testDamage();
        } else if (args[0].equals("1")) {
            testHealing();
        }
    }

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
