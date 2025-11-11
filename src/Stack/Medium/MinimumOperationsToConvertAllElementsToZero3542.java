// Problem: Minimum Operations to Convert All Elements to Zero (LeetCode 3542)
// URL: https://leetcode.com/problems/minimum-operations-to-convert-all-elements-to-zero/
//
// Description:
// You are given an integer array nums. 
// In one operation, you can choose an index i (0 <= i < n) such that nums[i] > 0, 
// and decrease all elements nums[0...i] by 1.
//
// Return the minimum number of operations required to make all elements of nums equal to 0.
//
// Example:
// Input: nums = [3, 1, 1, 2, 1, 1, 1]
// Output: 3
//
// Explanation:
// - First, decrease prefix [0..0] three times to make nums[0] = 0. 
// - Then, decrease prefix [0..3] two times to make nums[3] = 0.
// - Finally, decrease prefix [0..6] one time to make all elements zero.
// Hence, 3 distinct "starting points" of positive value segments cause 3 operations.
//
// Approach:
// 1. Use a **monotonic increasing stack** to find the index of the **previous smaller or equal element** for every element.
//    - For each element nums[i], find the closest index `j < i` such that nums[j] <= nums[i].
//    - If no such element exists, assign -1.
// 2. In `minOperations()`:
//    - Iterate through each element:
//       - If nums[i] is positive and either (a) no previous smaller element exists (`pSee[i] == -1`), 
//         or (b) the previous smaller element’s value is strictly less than nums[i],
//         then nums[i] marks the start of a new decreasing prefix operation.
//    - Increment the count for each such new starting point.
// 3. The total number of such points gives the minimum number of prefix operations needed.
//
// Time Complexity: O(n) — Each element pushed/popped at most once.
// Space Complexity: O(n) — Stack + result array.

import java.util.Stack;

public class MinimumOperationsToConvertAllElementsToZero3542 {

    // Helper method to find the index of previous smaller or equal element for each array element
    public int[] previousSmallestOrEqualElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> st = new Stack<>(); // Monotonic increasing stack storing indices
        int[] res = new int[n];            // Result array to store index of previous smaller or equal element

        for (int i = 0; i < n; i++) {
            // Maintain monotonic increasing stack:
            // Pop indices whose elements are greater than current arr[i]
            while (!st.isEmpty() && arr[st.peek()] > arr[i]) {
                st.pop();
            }

            // If stack is empty, no smaller/equal element exists → assign -1
            // Otherwise, top of stack gives the index of previous smaller/equal element
            res[i] = st.isEmpty() ? -1 : st.peek();

            // Push current index for future comparisons
            st.push(i);
        }

        return res;
    }

    // Main method to compute minimum operations to convert all elements to zero
    public int minOperations(int[] nums) {
        int[] pSee = previousSmallestOrEqualElement(nums); // Get previous smaller/equal element indices
        int n = nums.length;
        int minOps = 0;

        // Traverse array to count unique "starting points" of prefix decrement operations
        for (int i = 0; i < n; i++) {
            // A new operation is needed if:
            // - nums[i] > 0, and
            // - Either there is no previous smaller/equal element (pSee[i] == -1)
            //   or the previous smaller element has a smaller value (nums[pSee[i]] < nums[i])
            if (nums[i] > 0 && (pSee[i] == -1 || nums[pSee[i]] < nums[i])) {
                minOps++;
            }
        }

        return minOps;
    }

    // Driver method for local testing
    public static void main(String[] args) {
        MinimumOperationsToConvertAllElementsToZero3542 obj = new MinimumOperationsToConvertAllElementsToZero3542();
        int[] nums = {3, 1, 1, 2, 1, 1, 1};
        System.out.println(obj.minOperations(nums)); // Expected output: 3
    }
}
