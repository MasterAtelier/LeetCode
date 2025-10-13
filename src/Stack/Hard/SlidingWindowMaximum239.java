
/**
 * Problem: Sliding Window Maximum
 * URL: https://leetcode.com/problems/sliding-window-maximum/
 *
 * Description:
 * You are given an integer array `nums` and an integer `k`.
 * There is a sliding window of size `k` that moves from the left to the right across the array.
 * For each window position, return the maximum value within the window.
 *
 * Example:
 * Input:  nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 *
 * Explanation:
 * - For window [1,3,-1] → max = 3
 * - For window [3,-1,-3] → max = 3
 * - For window [-1,-3,5] → max = 5
 * - For window [-3,5,3] → max = 5
 * - For window [5,3,6] → max = 6
 * - For window [3,6,7] → max = 7
 *
 * Approach:
 * 1. Use a Deque (double-ended queue) to store indices of potential maximum elements.
 * 2. Maintain the Deque in decreasing order of their values in nums[].
 * 3. For each index i:
 *    - Remove indices from front that are out of the current window range (i - k + 1).
 *    - Remove indices from the back while current element >= element at those indices.
 *    - Add current index to the deque.
 *    - When i >= k - 1, the element at the front of the deque is the max for the current window.
 *
 * Time Complexity: O(N)
 *   Each element is added and removed from the deque at most once.
 *
 * Space Complexity: O(K)
 *   Deque can store up to k indices.
 */
import java.util.ArrayDeque;
import java.util.ArrayList;

public class SlidingWindowMaximum239 {

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        ArrayList<Integer> result = new ArrayList<>();
        ArrayDeque<Integer> deque = new ArrayDeque<>(); // stores indices of elements

        for (int i = 0; i < n; i++) {

            // Step 1: Remove elements out of this window
            if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Step 2: Maintain elements in decreasing order
            // Remove all elements smaller than or equal to current element
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            // Step 3: Add current element index
            deque.offerLast(i);

            // Step 4: Record the maximum for this window
            if (i >= k - 1) {
                result.add(nums[deque.peekFirst()]);
            }
        }

        // Convert result list to array
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }

        return res;
    }

    // ---------- Main Method for Testing ----------
    public static void main(String[] args) {
        SlidingWindowMaximum239 solver = new SlidingWindowMaximum239();

        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;

        int[] output = solver.maxSlidingWindow(nums, k);

        System.out.print("Sliding Window Maximums: ");
        for (int val : output) {
            System.out.print(val + " ");
        }
    }
}
