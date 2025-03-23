# Expression-Tree-Evaluation
A Java program that takes a fully parenthesized arithmetic expression as input, converts it to a binary expression tree, displays the tree, and evaluates the expression.

Key Features:

Implement a Node class to represent nodes in the binary tree.

Create a method to build the expression tree from a fully parenthesized input string.

Develop a method to evaluate the expression tree and return the result.

In the main method, prompt the user for input, build the tree and show the evaluation result.

Implement a method to display the tree structure of the expression tree.

Add support for exponentiation (^) operator.

Add support for decimal numbers in expressions.

Implement error handling for invalid input expressions.

Implement a simple command-line interface, allowing users to enter expressions at the command line and see results.

Add support for variables in expressions (e.g., x1, x2, x3).

Implement a method to update variable values interactively.

Add support for unary operators (e.g., negation).

Add support for functions (e.g., sin, cos, log).

Example inputs and expected outputs:

1. (sin(30) + cos(60))

Expected Output:

|--> +
    |--> sin
    |   |--> 30
    |--> cos
        |--> 60

The answer to the parenthesized expression is: 1.0

2. (log(x + 2) - (y ^ 2))

Expected Output:

|--> -
    |--> +
    |   |--> x
    |   |--> 2
    |--> ^
        |--> y
        |--> 2

Please enter a value for x: 1

Please enter a value for y: 2

The answer to the parenthesized expression is: -1.0

3.(log(sin(45) * cos(45)))

Expected Output:

|--> log
    |--> *
    |   |--> sin
    |   |   |--> 45
    |   |--> cos
    |       |--> 45

The answer to the parenthesized expression is: -0.6931471805599453

4.(3-(-2.5 * 4))

Expected Output:

|--> -
    |--> 3
    |--> *
        |--> -2.5
        |--> 4

The answer to the parenthesized expression is: 13.0

Instructions:

Follow prompts for user inputs.

Allows batch processing of expressions either through multiple command line arguments differentiated by space or the scanner which the program prompts the user for when no arguments are provided.

When using the command line to pass arguments please pass the expression as a string with " at the start and end.

Natural Log is being used, and for functions like sin and cos, numbers are converted to degrees.
