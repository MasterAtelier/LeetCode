// Problem: Minimum ASCII Delete Sum for Two Strings
// URL: https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
//
// Description:
// Given two strings s1 and s2, return the minimum ASCII delete sum needed to make the two strings equal.
// Deleting a character costs its ASCII value.
// The goal is to compute the minimum sum of deleted ASCII values from both strings so that they become identical.
//
// Example:
// Input: s1 = "delete", s2 = "leet"
// Output: 403
// Explanation:
// To make both strings equal, characters with ASCII sum = 403 must be deleted.
//
// Approach (Longest Common Subsequence Based):
// 1. Instead of directly calculating the minimum delete sum, compute the maximum ASCII sum of the
//    *common subsequence* that both strings can preserve.
//    Let this value be `maxKeep`.
// 2. Total ASCII sum of all characters in s1 + s2 is `sumTotal`.
// 3. To keep a common subsequence of ASCII sum `maxKeep`, the amount deleted must be:
//         sumTotal - 2 * maxKeep
//    because both strings remove everything except the common kept subsequence.
//
// Dynamic Programming:
// - dp[i][j] represents the maximum ASCII sum of the longest common subsequence between:
//       s1[0..i-1] and s2[0..j-1]
// - If characters match, include ASCII value:
//       dp[i][j] = dp[i-1][j-1] + ASCII(s1[i-1])
// - Otherwise, take the best between skipping one character:
//       dp[i][j] = max(dp[i-1][j], dp[i][j-1])
//
// Time Complexity: O(n * m)
// Space Complexity: O(n * m)

public class MinimumASCIIDeleteSumForTwoStrings712 {

    public int minimumDeleteSum(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        // dp[i][j] = maximum ASCII sum of LCS between s1[0..i-1] and s2[0..j-1]
        int[][] dp = new int[n + 1][m + 1];

        // Build DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Characters match → keep this character in LCS
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + s1.charAt(i - 1);
                } // Else, choose the best between skipping from s1 or skipping from s2
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Total ASCII sum of all characters from both strings
        int sumTotal = 0;
        String combo = s1 + s2;
        for (int i = 0; i < combo.length(); i++) {
            sumTotal += combo.charAt(i);
        }

        int maxKeep = dp[n][m];  // maximum ASCII sum we can preserve

        // Minimum delete sum = everything deleted except the common subsequence
        return sumTotal - 2 * maxKeep;
    }

    // Driver code for manual testing
    public static void main(String[] args) {
        MinimumASCIIDeleteSumForTwoStrings712 obj = new MinimumASCIIDeleteSumForTwoStrings712();
        String s1 = "delete";
        String s2 = "leet";
        int result = obj.minimumDeleteSum(s1, s2);
        System.out.println("Minimum ASCII Delete Sum: " + result); // Expected output: 403
    }
}
