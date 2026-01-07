import java.util.*;

/*
 ============================================================================
 PROBLEM: Maximum Level Sum of a Binary Tree (LeetCode 1161)
 ----------------------------------------------------------------------------
 Problem Link:
 https://leetcode.com/problems/maximum-level-sum-of-a-binary-tree/

 ----------------------------------------------------------------------------
 You are given the root of a binary tree.

 Each level of the tree has a "level sum", which is the sum of all node values
 present at that depth.

 Your task is to:
   - Compute the sum of values at each level
   - Find the level (1-indexed) that has the maximum sum
   - If multiple levels have the same maximum sum, return the smallest level

 ----------------------------------------------------------------------------
 EXAMPLE:
            1
           / \
          7   0
         / \
        7  -8

 Level 1 sum = 1
 Level 2 sum = 7 + 0 = 7
 Level 3 sum = 7 + (-8) = -1

 Maximum sum is 7 at level 2 → answer = 2

 ----------------------------------------------------------------------------
 APPROACH:
 We use Breadth-First Search (BFS) / Level Order Traversal because:
   - BFS naturally processes nodes level by level
   - We can calculate the sum of each level independently

 DATA STRUCTURE USED:
   - Queue (LinkedList) to maintain BFS order

 TIME COMPLEXITY:
   O(N) → each node is visited exactly once

 SPACE COMPLEXITY:
   O(N) → queue can store up to a full level of nodes
 ============================================================================
*/

public class MaximumLevelSumOfBinaryTree1161 {

    /*
     Definition of a binary tree node.
     Each node contains:
       - an integer value
       - a reference to the left child
       - a reference to the right child
    */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /*
     Returns the level number (1-indexed) that has the maximum sum.
    */
    public int maxLevelSum(TreeNode root) {

        // Stores the maximum sum found so far
        int maxSum = -1;

        // Tracks the current level number
        int currLevel = 0;

        // Queue for BFS traversal
        LinkedList<TreeNode> queue = new LinkedList<>();

        // Start BFS from root
        queue.add(root);

        // Level with maximum sum
        int maxSumLevel = -1;

        while (!queue.isEmpty()) {

            currLevel++;
            int size = queue.size();
            int levelSum = 0;

            // Process all nodes at the current level
            for (int i = 0; i < size; i++) {

                TreeNode currNode = queue.poll();
                levelSum += currNode.val;

                if (currNode.left != null) {
                    queue.add(currNode.left);
                }

                if (currNode.right != null) {
                    queue.add(currNode.right);
                }
            }

            // Update max sum and corresponding level
            if (levelSum > maxSum) {
                maxSum = levelSum;
                maxSumLevel = currLevel;
            }
        }

        return maxSumLevel;
    }

    public static void main(String[] args) {

        MaximumLevelSumOfBinaryTree1161 mls =
                new MaximumLevelSumOfBinaryTree1161();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(7);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(7);
        root.left.right = new TreeNode(-8);

        int result = mls.maxLevelSum(root);

        // Expected output: 2
        System.out.println(result);
    }
}
