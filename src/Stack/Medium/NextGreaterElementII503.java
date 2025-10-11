// Problem: Next Greater Element II
// URL: https://leetcode.com/problems/next-greater-element-ii/description/
//
// Description:
// Given a circular integer array nums (i.e., the next element of the last element is the first element of the array),
// return the "next greater number" for every element in nums.
// The Next Greater Number of a number x is the first greater number to its traversing-order next in the array.
// If it does not exist, output -1 for that element.
//
// Example:
// Input: nums = [1, 2, 1]
// Output: [2, -1, 2]
// Explanation: 
// - For the first 1, the next greater element is 2.
// - For 2, no greater element exists → -1.
// - For the last 1, the next greater element (in circular sense) is 2.
//
// Approach:
// 1. We simulate circular traversal by iterating the array twice (2 * n - 1 times).
// 2. Use a monotonic decreasing stack that keeps potential "next greater" candidates.
// 3. Traverse from right to left:
//    - Pop elements smaller than or equal to current nums[idx], since they can't be next greater for anyone left of idx.
//    - The top of the stack (if any) is the next greater element.
//    - Push current nums[idx] into the stack.
// 4. Use modulo indexing (i % n) to handle circular behavior.
//
// Time Complexity: O(n) — Each element pushed/popped at most once.
// Space Complexity: O(n) — Stack + result array.

import java.util.Stack;

public class NextGreaterElementII503 {

    public int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];       // Result array storing next greater element for each index
        Stack<Integer> stack = new Stack<>(); // Monotonic decreasing stack (stores potential next greater elements)

        // Traverse array twice (circular array simulation)
        for (int i = 2 * n - 1; i >= 0; i--) {
            int idx = i % n; // Use modulo to wrap around circularly

            // Maintain decreasing order in stack
            // Pop elements smaller or equal to current
            while (!stack.isEmpty() && stack.peek() <= nums[idx]) {
                stack.pop();
            }

            // If stack is empty, no next greater exists → -1
            // Otherwise, top of stack is the next greater number
            res[idx] = stack.isEmpty() ? -1 : stack.peek();

            // Push current number for future comparisons
            stack.push(nums[idx]);
        }

        return res;
    }

    // Driver method for local testing
    public static void main(String[] args) {
        NextGreaterElementII503 nextGreaterElementII503 = new NextGreaterElementII503();
        int[] nums = new int[]{1, 2, 1};
        int[] res = nextGreaterElementII503.nextGreaterElement(nums);

        // Print result
        for (int elem : res) {
            System.out.print(elem + " ");
        }
    }
}
