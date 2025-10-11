// Problem: Trapping Rain Water
// URL: https://leetcode.com/problems/trapping-rain-water/
//
// Description:
// Given n non-negative integers representing an elevation map where the width of each bar is 1,
// compute how much water can be trapped after raining.
//
// Example:
// Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
// Output: 6
// Explanation:
// Visualization:
//        |
//    |~~~|~|~|
//  _|_|_|_|_|_|_
//  0 1 0 2 1 0 1 3 2 1 2 1
// Total trapped water = 6 units.
//
// Approach (Two-Pointer Technique):
// 1. Maintain two pointers `l` (left) and `r` (right) starting at the ends of the array.
// 2. Keep track of the maximum heights encountered so far from both ends — `lMax` and `rMax`.
// 3. Move the pointer corresponding to the smaller height because the trapped water depends on
//    the smaller boundary between left and right.
//    - If height[l] <= height[r]:
//        • If lMax > height[l], water trapped = lMax - height[l]
//        • Else, update lMax = height[l]
//        • Move l++
//    - Else (height[r] < height[l]):
//        • If rMax > height[r], water trapped = rMax - height[r]
//        • Else, update rMax = height[r]
//        • Move r--
// 4. Continue until l and r meet.
// 5. The accumulated total is the answer.
//
// Time Complexity: O(n)  — Each index is processed once.
// Space Complexity: O(1) — Constant extra space used.

public class TrappingRainWater42 {

    public int trappingRainWater(int[] height) {
        int n = height.length;
        int total = 0;      // Stores total water trapped
        int l = 0, r = n - 1; // Two pointers: left and right
        int lMax = 0, rMax = 0; // Track maximum heights from both ends

        // Process until the two pointers meet
        while (l < r) {
            // Move from the side with smaller current height
            if (height[l] <= height[r]) {
                // If left max is greater, water can be trapped
                if (lMax > height[l]) {
                    total += lMax - height[l];
                } else {
                    // Otherwise, update the left max
                    lMax = height[l];
                }
                l++;
            } else {
                // Right side logic mirrors the left
                if (rMax > height[r]) {
                    total += rMax - height[r];
                } else {
                    rMax = height[r];
                }
                r--;
            }
        }

        return total;
    }

    // Driver method for testing
    public static void main(String[] args) {
        TrappingRainWater42 trappingRainWater42 = new TrappingRainWater42();
        int[] height = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        int ans = trappingRainWater42.trappingRainWater(height);
        System.out.println("Water Trapped " + ans);
    }
}
