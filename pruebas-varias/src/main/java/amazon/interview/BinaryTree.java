package amazon.interview;

// Assumptions:
// 1. Exist the BinaryTree class modeled as follows
public class BinaryTree<T> {

    private Node root;

    private class Node {
        private Node left, right;
        private T elem;

        private Node(T elem) {
            this.elem = elem;
        }
    }

    public int height(Node n) {
        if (n == null) {
            return 0;
        } else {
            return 1 + Math.max(height(n.left), height(n.right));
        }
    }

    // Assuming N: Number of elements in the tree
    // Time complexity = 3N
    // Space complexity = N
    public boolean isBalanced(Node n) {
        boolean balanced = false;
        if (n == null) {
            balanced = true;
        } else {
            int rightH = height(n.right);
            int leftH = height(n.left);
            if (Math.abs(rightH - leftH) <= 1) {
                balanced = isBalanced(n.right) && isBalanced(n.left);
            }
        }
        return balanced;
    }
}
