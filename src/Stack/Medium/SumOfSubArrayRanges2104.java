
import java.util.Stack;

/**
 * LeetCode Problem: Sum of Subarray Ranges URL:
 * https://leetcode.com/problems/sum-of-subarray-ranges/
 *
 * We compute: sum over all subarrays of (max(subarray) − min(subarray)) =
 * sumOfSubarrayMaxs(nums) − sumOfSubarrayMins(nums)
 */
public class SumOfSubArrayRanges2104 {

    /**
     * Finds the index of the next smaller element to the right for each
     * position. For each i, nextSmallest[i] = index j > i such that arr[j] <
     * arr[i] and j is minimal. If no such j exists, nextSmallest[i] = -1.
     *
     * Uses a monotonic increasing stack (by value) scanning from right to left.
     */
    private int[] findNextSmallestElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return res;
    }

    /**
     * Finds the index of the previous smaller or equal element to the left for
     * each position. For each i, prevSmallOrEq[i] = index j < i such that
     * arr[j] ≤ arr[i] and j is maximal. If no such j exists, prevSmallOrEq[i] =
     * -1.
     *
     * Uses a monotonic increasing stack scanning from left to right.
     */
    private int[] findPreviousSmallestOrEqualElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return res;
    }

    /**
     * Finds the index of the next greater element to the right for each
     * position. For each i, nextGreater[i] = index j > i such that arr[j] >
     * arr[i] and j is minimal. If no such j exists, nextGreater[i] = -1.
     *
     * Uses a monotonic decreasing stack scanning from right to left.
     */
    private int[] findNextGreatestElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] <= arr[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return res;
    }

    /**
     * Finds the index of the previous greater or equal element to the left for
     * each position. For each i, prevGreatOrEq[i] = index j < i such that
     * arr[j] ≥ arr[i] and j is maximal. If no such j exists, prevGreatOrEq[i] =
     * -1.
     *
     * Uses a monotonic decreasing stack scanning from left to right.
     */
    private int[] findPreviousGreatestOrEqualElement(int[] arr) {
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                stack.pop();
            }
            res[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        return res;
    }

    /**
     * Computes the sum of the minimums of all subarrays of arr. Each arr[i]
     * contributes as the minimum for those subarrays where it is the smallest
     * element and no smaller (strictly) lies in the subarray.
     *
     * left = i − prevSmallOrEq[i] right = (nextSmallest[i] != -1 ?
     * nextSmallest[i] − i : n − i) contribution = arr[i] * left * right
     */
    public long sumOfSubarrayMins(int[] arr) {
        int n = arr.length;
        int[] nextSmallest = findNextSmallestElement(arr);
        int[] prevSmallOrEq = findPreviousSmallestOrEqualElement(arr);
        long total = 0L;

        for (int i = 0; i < n; i++) {
            long left = i - prevSmallOrEq[i];
            long right = (nextSmallest[i] != -1) ? nextSmallest[i] - i : n - i;
            total += (long) arr[i] * left * right;
        }
        return total;
    }

    /**
     * Computes the sum of the maximums of all subarrays of arr. Similarly, each
     * arr[i] contributes as the maximum for subarrays where no strictly greater
     * element lies in that subarray.
     *
     * left = i − prevGreatOrEq[i] right = (nextGreater[i] != -1 ?
     * nextGreater[i] − i : n − i) contribution = arr[i] * left * right
     */
    public long sumOfSubarrayMaxs(int[] arr) {
        int n = arr.length;
        int[] nextGreater = findNextGreatestElement(arr);
        int[] prevGreatOrEq = findPreviousGreatestOrEqualElement(arr);
        long total = 0L;

        for (int i = 0; i < n; i++) {
            long left = i - prevGreatOrEq[i];
            long right = (nextGreater[i] != -1) ? nextGreater[i] - i : n - i;
            total += (long) arr[i] * left * right;
        }
        return total;
    }

    /**
     * Returns the sum of ranges (max − min) for **all subarrays** of nums.
     * Based on the identity: sumOfSubarrayRanges = sumOfSubarrayMaxs(nums) −
     * sumOfSubarrayMins(nums)
     */
    public long subArrayRanges(int[] nums) {
        return sumOfSubarrayMaxs(nums) - sumOfSubarrayMins(nums);
    }

    public static void main(String[] args) {
        SumOfSubArrayRanges2104 solver = new SumOfSubArrayRanges2104();
        int[] nums = {1, 2, 3};
        long total = solver.subArrayRanges(nums);
        System.out.println("total " + total);
    }
}
