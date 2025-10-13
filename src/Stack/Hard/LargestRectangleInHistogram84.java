// Problem: Largest Rectangle in Histogram
// URL: https://leetcode.com/problems/largest-rectangle-in-histogram/
//
// Description:
// Given an array of integers heights representing the histogram’s bar height where the width of each bar is 1,
// return the area of the largest rectangle that can be formed in the histogram.
//
// Example:
// Input: heights = [2,1,5,6,2,3]
// Output: 10
// Explanation:
// The largest rectangle is formed by bars of height 5 and 6 (width 2) → area = 5 * 2 = 10.
//
// Approach (Monotonic Stack):
// 1. For each bar, we need to find:
//    - Previous Smaller Element (PSE) → first smaller bar to the left
//    - Next Smaller Element (NSE) → first smaller bar to the right
// 2. The width of the rectangle that can be formed using heights[i] as the smallest bar is (NSE - PSE - 1).
// 3. To efficiently find PSE and NSE, we use a **monotonic increasing stack**.
// 4. Traverse from left to right:
//    - While the current bar is smaller than the top of the stack, pop from the stack.
//      For each popped bar, calculate the area using the current index as NSE.
//    - Push the current index onto the stack.
// 5. After the loop, process any remaining bars in the stack using `n` as the NSE.
// 6. Track and return the maximum area found.
//
// Time Complexity: O(n) — Each element is pushed/popped at most once.
// Space Complexity: O(n) — Stack stores indices of bars.

import java.util.Stack;

public class LargestRectangleInHistogram84 {

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int area = 0; // Tracks maximum area found
        Stack<Integer> stack = new Stack<>(); // Monotonic increasing stack to store indices

        // Traverse through each bar
        for (int i = 0; i < n; i++) {
            // If current bar is smaller than the bar at stack top,
            // we've found the next smaller element for stack.top()
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int idx = stack.pop(); // Index of the bar whose area we can now compute
                int nse = i; // Current index acts as the next smaller element
                int pse = stack.isEmpty() ? -1 : stack.peek(); // Previous smaller element index
                int width = nse - pse - 1; // Width between PSE and NSE
                area = Math.max(area, heights[idx] * width); // Compute area and update max
            }
            stack.push(i);
        }

        // Process remaining bars in stack (no NSE found, use n)
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            int nse = n; // No smaller element on the right
            int pse = stack.isEmpty() ? -1 : stack.peek();
            int width = nse - pse - 1;
            area = Math.max(area, heights[idx] * width);
        }

        return area;
    }

    // Optional test driver for local run
    public static void main(String[] args) {
        LargestRectangleInHistogram84 solver = new LargestRectangleInHistogram84();
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println("Largest Rectangle Area: " + solver.largestRectangleArea(heights)); // Expected: 10
    }
}
