import java.util.Scanner;

public class AVLTree {

    private class Node {
        OrderedPair data;
        Node left, right;
        int height;

        Node(OrderedPair data) {
            this.data = data;
            this.height = 1;
        }
    }

    private Node root;

    // Get height of the node
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Get balance factor of the node
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Right rotate subtree rooted with node y
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // Left rotate subtree rooted with node x
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Insert an ordered pair into the AVL tree
    public void insert(OrderedPair pair) {
        root = insert(root, pair);
    }

    private Node insert(Node node, OrderedPair pair) {
        // 1. Perform the normal BST insertion
        if (node == null) {
            return new Node(pair);
        }

        if (pair.compareTo(node.data) < 0) {
            node.left = insert(node.left, pair);
        } else if (pair.compareTo(node.data) > 0) {
            node.right = insert(node.right, pair);
        } else {
            return node; // No duplicates allowed
        }

        // 2. Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Get the balance factor to check whether this node became unbalanced
        int balance = getBalance(node);

        // 4. If the node becomes unbalanced, then 4 cases

        // Left Left Case
        if (balance > 1 && pair.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && pair.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && pair.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && pair.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Return the unchanged node pointer
    }

    // Delete a node
    public void delete(OrderedPair pair) {
        root = delete(root, pair);
    }

    private Node delete(Node root, OrderedPair pair) {
        // Base case: If the tree is empty
        if (root == null) {
            return root;
        }

        // Recursive case: If the node to be deleted is smaller or larger than root
        if (pair.compareTo(root.data) < 0) {
            root.left = delete(root.left, pair);
        } else if (pair.compareTo(root.data) > 0) {
            root.right = delete(root.right, pair);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            root.data = minValue(root.right);

            // Delete the inorder successor
            root.right = delete(root.right, root.data);
        }

        // If the tree has only one node, return it
        if (root == null) {
            return root;
        }

        // Update height of the current node
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // Get the balance factor of this node
        int balance = getBalance(root);

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private OrderedPair minValue(Node node) {
        OrderedPair minValue = node.data;
        while (node.left != null) {
            minValue = node.left.data;
            node = node.left;
        }
        return minValue;
    }

    // In-order traversal to print the tree with node numbers
    private void printInOrder(Node node, int index) {
        if (node != null) {
            printInOrder(node.left, 2 * index);
            System.out.println(index + ". " + node.data);
            printInOrder(node.right, 2 * index + 1);
        }
    }

    public void printTree() {
        printInOrder(root, 1); // Start printing from the root at index 1
    }

    // Main method for user interaction
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Insert an OrderedPair");
            System.out.println("2. Delete an OrderedPair");
            System.out.println("3. Print the tree");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter x: ");
                double x = scanner.nextDouble();
                System.out.print("Enter y: ");
                double y = scanner.nextDouble();
                tree.insert(new OrderedPair(x, y));
            } else if (choice == 2) {
                System.out.print("Enter x: ");
                double x = scanner.nextDouble();
                System.out.print("Enter y: ");
                double y = scanner.nextDouble();
                tree.delete(new OrderedPair(x, y));
            } else if (choice == 3) {
                tree.printTree();
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        scanner.close();
    }
}
