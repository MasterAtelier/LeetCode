// Problem: Sum of Subarray Minimums
// URL: https://leetcode.com/problems/sum-of-subarray-minimums/
//
// Description:
// Given an integer array arr, find the sum of the minimum value of every subarray of arr.
// Since the answer can be very large, return it modulo 1_000_000_007.
//
// Example:
// Input: arr = [3,1,2,4]
// Output: 17
// Explanation:
// Subarrays are:
// [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]
// Minimums are: 3 + 1 + 2 + 4 + 1 + 1 + 2 + 1 + 1 + 1 = 17
//
// Approach:
// 1. For each element arr[i], determine how many subarrays it contributes as the *minimum element*.
// 2. To do that, find:
//    - Previous Smaller or Equal Element index (to the left)
//    - Next Smaller Element index (to the right)
// 3. The count of subarrays where arr[i] is the minimum = (i - leftIndex) * (rightIndex - i)
// 4. Multiply this count by arr[i] and sum over all i.
// 5. Use modulo 1_000_000_007 for large values.
//
// Time Complexity: O(n) — Each element is pushed/popped from stack at most once.
// Space Complexity: O(n) — For two auxiliary arrays and stacks.

import java.util.Stack;

public class SumOfSubArrayMinimums907 {

    private final int MOD = 1_000_000_007;

    // Helper method to find index of next smaller element for each index
    // Returns an array where res[i] = index of next smaller element to the right, else -1
    private int[] findNextSmallestElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        // Traverse from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Maintain increasing stack (by values)
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }

            // Store next smaller element index or -1 if none
            res[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current index for future comparisons
            stack.push(i);
        }

        return res;
    }

    // Helper method to find index of previous smaller or equal element for each index
    // Returns an array where res[i] = index of previous smaller/equal element to the left, else -1
    private int[] findPreviousSmallestOrEqualElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        // Traverse from left to right
        for (int i = 0; i < n; i++) {
            // Maintain strictly increasing stack
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }

            // Store previous smaller/equal element index or -1 if none
            res[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current index for future comparisons
            stack.push(i);
        }

        return res;
    }

    // Core function: computes sum of minimums across all subarrays
    public int sumOfSubarrayMins(int[] arr) {
        int n = arr.length;
        int[] nextSmallestElement = findNextSmallestElement(arr);
        int[] previousSmallestOrEqualElement = findPreviousSmallestOrEqualElement(arr);
        long total = 0; // Use long to avoid overflow during intermediate calculations

        for (int i = 0; i < n; i++) {
            // Distance to previous smaller/equal element (number of choices on left)
            int left = i - previousSmallestOrEqualElement[i];

            // Distance to next smaller element (number of choices on right)
            int right = (nextSmallestElement[i] != -1) ? nextSmallestElement[i] - i : n - i;

            // Contribution of arr[i] to total sum
            total = (total + ((long) arr[i] * left * right) % MOD) % MOD;
        }

        return (int) total;
    }

    // Driver code for testing
    public static void main(String[] args) {
        SumOfSubArrayMinimums907 sumOfSubArrayMinimums907 = new SumOfSubArrayMinimums907();
        int[] arr = new int[]{3, 1, 2, 4};
        int total = sumOfSubArrayMinimums907.sumOfSubarrayMins(arr);
        System.out.println("total " + total);
    }
}
