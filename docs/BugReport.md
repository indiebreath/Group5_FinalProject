# Issue 1

Character's item HashMap unable to be edited by init function.

Found by: Mei Waterman

Solution: delete static type initItems function and instead move creation and initialisation of items hashmap to constructor for the class.

# Issue 2

Tests class not properly selecting the testing method based on the cli argument passed to the class call.

Found by: Mei Waterman

Solution: use Array.equals for comparison of the cli argument, and only search for a single argument per class call.

# Issue 3

Math.clamp function for Character's takeDamage and heal functions not properly clamping the value.

Found by: Mei Waterman

Solution: have health value be set to the result of the Math.clamp function instead of calling the Math.clamp function on it's own.
