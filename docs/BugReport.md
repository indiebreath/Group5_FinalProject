# Issue 1

getChoice if statement unable to properly detect and parse user input, regularly giving either old inputs or completely wrong inputs regardless.

Found by: Mei Waterman

Solution: instead of using comparison operators (==) implemented custom containsInt method that checks is one int is within another int at all. Not the best idea in all contexts, but only way to fix current issue.

# Issue 2

Character's item HashMap unable to be edited by init function.

Found by: Mei Waterman

Solution: delete static type initItems function and instead move creation and initialisation of items hashmap to constructor for the class.

# Issue 3

Tests class not properly selecting the testing method based on the cli argument passed to the class call.

Found by: Mei Waterman

Solution: use Array.equals for comparison of the cli argument, and only search for a single argument per class call.

# Issue 4

Math.clamp function for Character's takeDamage and heal functions not properly clamping the value.

Found by: Mei Waterman

Solution: have health value be set to the result of the Math.clamp function instead of calling the Math.clamp function on it's own.

# Issue 5

Program would bug out whenever any user input was made within the combat method, causing the entire program to crash whenever an attempt to make a choice was made.

Found by: Mei Waterman

Solution: have one global instance of the Scanner, which is passed around to every function that needs it rather than each function initialising and deinitialising their own instance as needed.

# Issue 6

Program would error out with null exception when calling the attack method within GameCharacter.

Found by: Mei Waterman

Solution: add null-safe comparison, making the program return false if the if the value is null rather than erroring.

# Issue 7

Program would hang whenever entering the hazard room, whether it be from normal traversal or by directly setting the player's position.

Found by: Mei Waterman

Solution: actually add the hazmat suit to the player's inventory, which wasn't initialised thuis returned null. No idea why it just hung here but errored on others but oh well.

# Issue 8

Errors given whenever trying to run Main.java file, saying that it cannot access GameCharacter or Util classes.

Found by: Mei Waterman

Solution: Compile both Utils.java and GameCharacter.java before running Main.java.

# Issue 9

Illegal access error thrown whenever trying to access data from GameCharacter instance such as location or inventory.

Found by: Mei Waterman

Solution: Also compile Main.java and run through "java Main" command rather than old method.
