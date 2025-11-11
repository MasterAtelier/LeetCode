// Problem: Longest Common Subsequence
// URL: https://leetcode.com/problems/longest-common-subsequence/description/
//
// Description:
// Given two strings text1 and text2, return the length of their longest common subsequence.
// A subsequence is a sequence that appears in the same relative order, but not necessarily contiguous.
//
// Example:
// Input: text1 = "abcde", text2 = "ace"
// Output: 3
// Explanation: The longest common subsequence is "ace" and its length is 3.
//
// Approach (Dynamic Programming - Tabulation):
// 1. Define dp[i][j] as the length of the LCS between the prefixes text1[0..i-1] and text2[0..j-1].
// 2. Recurrence Relation:
//    - If characters match: text1[i-1] == text2[j-1], then 
//         dp[i][j] = 1 + dp[i-1][j-1]
//    - Otherwise, take the maximum between excluding one character from either string:
//         dp[i][j] = max(dp[i-1][j], dp[i][j-1])
// 3. Base Case:
//    - If either string is empty (i == 0 or j == 0), then dp[i][j] = 0
// 4. The answer is stored in dp[n][m], where n and m are lengths of the two strings.
//
// Time Complexity: O(n * m)
// Space Complexity: O(n * m)
// (Can be optimized to O(min(n, m)) using two 1D arrays)

public class LongestCommonSubsequence1143 {

    // Computes the length of the Longest Common Subsequence between two strings
    private int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();  // Length of first string
        int m = text2.length();  // Length of second string

        // dp[i][j] represents length of LCS between text1[0..i-1] and text2[0..j-1]
        int[][] dp = new int[n + 1][m + 1];

        // Build the dp table iteratively
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                // Base Case: If either string is empty
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } // If characters match, include it in LCS
                else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } // Otherwise, exclude one character from either string and take max
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // The bottom-right cell contains the final LCS length
        return dp[n][m];
    }

    // Driver method for local testing
    public static void main(String[] args) {
        LongestCommonSubsequence1143 obj = new LongestCommonSubsequence1143();
        String text1 = "abcde";
        String text2 = "ace";
        int result = obj.longestCommonSubsequence(text1, text2);
        System.out.println("Length of Longest Common Subsequence: " + result); // Expected output: 3
    }
}
