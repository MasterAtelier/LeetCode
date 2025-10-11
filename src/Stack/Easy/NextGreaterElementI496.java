// Problem: Next Greater Element I
// URL: https://leetcode.com/problems/next-greater-element-i/description/
// 
// Description:
// You are given two integer arrays nums1 and nums2 where nums1 is a subset of nums2.
// For each element x in nums1, find the next greater element of x in nums2.
// The next greater element of x is the first greater number to its right in nums2.
// If it does not exist, return -1 for that element.
//
// Example:
// Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
// Output: [-1,3,-1]
//
// Approach:
// 1. We process nums2 from right to left using a monotonic stack.
// 2. For each element in nums2:
//    - Pop smaller or equal elements from the stack since they can never be the "next greater" for anyone to their left.
//    - The current top of stack (if any) is the next greater element for nums2[j].
//    - Store this mapping in a HashMap: hm.put(nums2[j], nextGreaterValue).
// 3. Finally, for each element in nums1, lookup its next greater value in the map.
// 
// Time Complexity: O(n2 + n1) ≈ O(n) — each element pushed/popped at most once.
// Space Complexity: O(n2) — for the stack and hashmap.

import java.util.HashMap;
import java.util.Stack;

public class NextGreaterElementI496 {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        int[] res = new int[n1]; // Result array to store next greater elements
        Stack<Integer> stack = new Stack<>(); // Monotonic decreasing stack
        HashMap<Integer, Integer> hm = new HashMap<>(); // Maps each num2 element to its next greater element

        // Traverse nums2 from right to left
        for (int j = n2 - 1; j >= 0; j--) {
            // Maintain a decreasing stack — pop all elements <= current
            while (!stack.empty() && stack.peek() <= nums2[j]) {
                stack.pop();
            }

            // If stack is empty, no greater element exists to the right → store -1
            // Else, the top of stack is the next greater element
            hm.put(nums2[j], stack.empty() ? -1 : stack.peek());

            // Push current element into stack
            stack.push(nums2[j]);
        }

        // Build result for nums1 using precomputed hashmap
        for (int i = 0; i < n1; i++) {
            res[i] = hm.getOrDefault(nums1[i], -1);
        }

        return res;
    }

    // Driver code for local testing
    public static void main(String[] args) {
        NextGreaterElementI496 nge = new NextGreaterElementI496();
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        int[] res = nge.nextGreaterElement(nums1, nums2);

        // Print result array
        for (int r : res) {
            System.out.print(r + " ");
        }
    }
}
