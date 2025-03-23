import java.util.Scanner;
import java.util.Stack;

//Java program that takes a fully parenthesized arithmetic expression as input, converts it to a binary expression tree, displays the tree, and evaluates the expression.
public class BinaryExpressionTree {

    //Main method
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        BinaryExpressionTree tree = new BinaryExpressionTree();

        if (args.length > 0) {
            for (String input : args) {
                input = input.trim();
                if (!input.isEmpty()) {
                    try {
                        Node root = tree.treeBuilder(input);
                        tree.showTree(root, "", false);
                        System.out.println();
                        double answer = tree.solve(root);
                        System.out.println("The answer to the parenthesized expression: " + input + " is: " + answer);
                    } catch (Exception e) {
                        System.out.println("There is an error in expression: " + input + "is: " + e.getMessage());
                    }
                }
            }
        } else {

            while (true) {
                System.out.println("Please enter a fully parenthesized expression:");
                String input = scanner.nextLine();
                try {
                    Node root = tree.treeBuilder(input);
                    tree.showTree(root,"",false);
                    System.out.println();
                    double answer = tree.solve(root);
                    System.out.println("The answer to the parenthesized expression is: " + answer);
                } catch (Exception e) {
                    System.out.println("There is an error: " + e.getMessage());
                }
            }
        }
    }

    // Builds tree from an arithmetic expression
    public Node treeBuilder(String input) {
        Stack<Node> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (ch == '(') {
                continue; // Continue from open parenthesis
            } else if (ch == '.' || Character.isDigit(ch)) {
                // Makes a string for the entire number
                StringBuilder buildString = new StringBuilder();
                while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                    buildString.append(input.charAt(i++)); //incrememnt index
                }
                i--;
                Node node = new Node(buildString.toString());
                stack.push(node);
            } else if (ch == ')') {
                Node rightChild = stack.pop();
                if (stack.isEmpty()) { // pops only one node from the stack
                    stack.push(rightChild); 
                    continue;
                }

                Node operator = stack.pop();

                if (isFunction(operator.value)) { // checks to see if it is a function
                    operator.left = rightChild;
                } else {
                    Node leftChild = stack.pop();
                    operator.left = leftChild;
                    operator.right = rightChild;
                }
                // push the subtree back
                stack.push(operator);
            } else if (ch == '/' || ch == '^' || ch == '+' || ch == '-' || ch == '*') { // Create node for operator
                if (ch == '-' && (i == 0 || input.charAt(i - 1) == '(' || input.charAt(i - 1) == '+' || input.charAt(i - 1) == '-' || input.charAt(i - 1) == '*' || input.charAt(i - 1) == '/' || input.charAt(i - 1) == '^')) {
                    if (i + 1 < input.length() && (Character.isDigit(input.charAt(i + 1)) || input.charAt(i + 1) == '.')) {
                        StringBuilder buildString = new StringBuilder();
                        buildString.append('-'); 
                        i++; 
                        while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                            buildString.append(input.charAt(i++));
                        }
                        i--; 
                        Node node = new Node(buildString.toString());
                        stack.push(node); 
                    } else {
                        Node negativeNode = new Node("*");
                        Node node = new Node("-1");
                        stack.push(node);
                        stack.push(negativeNode);
                    }
                } else {
                    Node node = new Node(String.valueOf(ch));
                    stack.push(node);
            }
                
            } else if (Character.isLetter(ch)) {//checks for variables and functions
                String substring = input.substring(i, Math.min(i + 3, input.length()));
                if (substring.equals("sin") || substring.equals("cos") || substring.equals("log")) {//checks for functions
                    Node node = new Node(substring);
                    stack.push(node);
                    i += 2; // skip 2 characters as is function
                }else{ //else is for variables
                StringBuilder stringBuild = new StringBuilder();
                
                while (i < input.length() && (Character.isLetter(input.charAt(i)) || Character.isDigit(input.charAt(i)))) {
                    stringBuild.append(input.charAt(i++));
                }
                i--;
                Node node = new Node(stringBuild.toString());
                stack.push(node);
            }
            }
        }
        return stack.pop();
    }

    // Solves the expression and gives the answer using recursion
    public double solve(Node node) {
        if (node == null) return 0;

        if (node.left == null && node.right == null) { //Checks if it is a leaf
            if (Character.isLetter(node.value.charAt(0))) { //checks if it is a variable
                @SuppressWarnings("resource")
                Scanner scanner = new Scanner(System.in);
                System.out.print("Please enter a value for " + node.value + ": ");
                return scanner.nextDouble();
            }
            return Double.parseDouble(node.value);
        }

        double lValue = solve(node.left);
        double rValue = node.right != null ? solve(node.right) : 0; // Makes sure there is a right child

        // Apply the operator at this node
        switch (node.value) {
            case "^": // Implementation of exponents
                return Math.pow(lValue, rValue);
                // For functions uses degrees
            case "sin":
                return Math.sin(Math.toRadians(lValue)); 
            case "cos":
                return Math.cos(Math.toRadians(lValue)); 
            case "log":
                if (lValue <= 0) throw new ArithmeticException("There is a logarithm of a negative number");
                return Math.log(lValue); // It is natural log
            case "+":
                return lValue + rValue;
            case "-":
                return lValue - rValue;
            case "*":
                return lValue * rValue;
            case "/":
                if (rValue == 0) throw new ArithmeticException("Cannot divide by zero");
                return (float) lValue / rValue;
            default:
                throw new IllegalArgumentException("Invalid operator please check your input");
        }
    }

    //Checks if its a function (sin, cos, log)
    private boolean isFunction(String value) {
        return value.equals("sin") || value.equals("log") || value.equals("cos");
    }

    // Shows the tree
    public void showTree(Node root, String space, boolean isLeft) {
        if (root == null) return;
        System.out.print(space);
        System.out.print("|--> ");
        System.out.println(root.value);
        String childPrefix = space + (isLeft ? "|   " : "    ");
        if (root.left != null) {
            showTree(root.left, childPrefix, true);
        }
        if (root.right != null) {
            showTree(root.right, childPrefix, false);
        }
    }
}
