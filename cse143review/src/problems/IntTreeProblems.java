package problems;

import datastructures.IntTree;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.IntTree.IntTreeNode;

/**
 * See the spec on the website for tips and example behavior.
 *
 * Also note: you may want to use private helper methods to help you solve these problems.
 * YOU MUST MAKE THE PRIVATE HELPER METHODS STATIC, or else your code will not compile.
 * This happens for reasons that aren't the focus of this assignment and are mostly skimmed over in
 * 142 and 143. If you want to know more, you can ask on the discussion board or at office hours.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `IntTree` objects
 * - do not construct new `IntTreeNode` objects (though you may have as many `IntTreeNode` variables
 *      as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the tree only by modifying
 *      links between nodes.
 */

public class IntTreeProblems {

    /**
     * Computes and returns the sum of the values multiplied by their depths in the given tree.
     * (The root node is treated as having depth 1.)
     */
    public static int depthSum(IntTree tree) {
        int i = 1;
        return depthSumHelper(tree.overallRoot, i);
    }

    private static int depthSumHelper(IntTreeNode treeNode, int i) {
        if (treeNode == null) {
            return 0;
        } else {
            return i * treeNode.data + depthSumHelper(treeNode.left, i + 1) + depthSumHelper(treeNode.right, i + 1);
        }
    }

    /**
     * Removes all leaf nodes from the given tree.
     */
    public static void removeLeaves(IntTree tree) {

        tree.overallRoot = rmLeavesHelper(tree.overallRoot);

    }

    private static IntTreeNode rmLeavesHelper(IntTreeNode treeNode) {

        if (treeNode != null) {
            if (treeNode.left == null && treeNode.right == null) {
                treeNode = null;
            } else {
                treeNode.left = rmLeavesHelper(treeNode.left);
                treeNode.right = rmLeavesHelper(treeNode.right);
            }
        }
        return treeNode;
    }

    /**
     * Removes from the given BST all values less than `min` or greater than `max`.
     * (The resulting tree is still a BST.)
     */
    public static void trim(IntTree tree, int min, int max) {

        tree.overallRoot = trimHelper(tree.overallRoot, min, max);

    }

    private static IntTreeNode trimHelper(IntTreeNode treeNode, int min, int max) {

        if (treeNode == null) {
            return null;
        }

        if (treeNode.data < min) {
            return trimHelper(treeNode.right, min, max);
        }

        if (treeNode.data > max) {
            return trimHelper(treeNode.left, min, max);
        }

        treeNode.left = trimHelper(treeNode.left, min, max);
        treeNode.right = trimHelper(treeNode.right, min, max);

        return treeNode;
    }
}
