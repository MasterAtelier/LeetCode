// Problem: Maximal Rectangle
// URL: https://leetcode.com/problems/maximal-rectangle/
//
// Description:
// Given a binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's
// and return its area.
//
// Example:
// Input:
// matrix = [
//   ['1','0','1','0','0'],
//   ['1','0','1','1','1'],
//   ['1','1','1','1','1'],
//   ['1','0','0','1','0']
// ]
// Output: 6
// Explanation:
// The largest rectangle containing only 1's has an area of 6.
//
// Approach (Histogram + Dynamic Heights):
// 1. Treat each row of the matrix as the base of a histogram.
//    - For each cell matrix[i][j]:
//      • If it's '1', increase height[j] by 1 (extend the bar).
//      • If it's '0', reset height[j] to 0.
// 2. For every row, compute the **largest rectangle in histogram** using the heights array.
//    - Use a monotonic increasing stack to find the previous and next smaller elements for each bar.
//    - Area for each bar = height * (rightBoundary - leftBoundary - 1).
// 3. The maximum area among all rows is the answer.
//
// Time Complexity: O(n * m) — Each cell is processed once.
// Space Complexity: O(m) — Additional stack and height array per row.

import java.util.Stack;

public class MaximalRectangle85 {

    // Helper function: computes largest rectangle in a histogram
    // Reused from LeetCode 84 (Largest Rectangle in Histogram)
    private int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int area = 0;
        Stack<Integer> stack = new Stack<>();

        // Traverse all bars
        for (int i = 0; i < n; i++) {
            // Maintain a non-decreasing stack
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int idx = stack.pop();
                int nse = i; // Next smaller element index
                int pse = stack.isEmpty() ? -1 : stack.peek(); // Previous smaller element index
                area = Math.max(area, heights[idx] * (nse - pse - 1));
            }
            stack.push(i);
        }

        // Process remaining bars (no NSE found → use n)
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            int nse = n;
            int pse = stack.isEmpty() ? -1 : stack.peek();
            area = Math.max(area, heights[idx] * (nse - pse - 1));
        }

        return area;
    }

    // Main function: computes maximal rectangle in a binary matrix
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // intMatrix[i][j] will represent "height" of histogram bar at column j up to row i
        int[][] intMatrix = new int[n][m];

        // Compute cumulative heights for each column
        for (int j = 0; j < m; j++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                sum += matrix[i][j] - '0'; // Convert '1'/'0' char to int
                if (matrix[i][j] == '0') {
                    sum = 0; // Reset if we encounter a '0'
                }
                intMatrix[i][j] = sum;
            }
        }

        int area = 0;
        // For each row (as histogram base), find the largest rectangle area
        for (int i = 0; i < n; i++) {
            area = Math.max(area, largestRectangleArea(intMatrix[i]));
        }

        return area;
    }

    // Optional test driver
    public static void main(String[] args) {
        MaximalRectangle85 solver = new MaximalRectangle85();
        char[][] matrix = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
        };
        System.out.println("Maximal Rectangle Area: " + solver.maximalRectangle(matrix)); // Expected: 6
    }
}
