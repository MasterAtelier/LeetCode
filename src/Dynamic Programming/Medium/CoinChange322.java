// Problem: Coin Change
// URL: https://leetcode.com/problems/coin-change/
//
// Description:
// You are given an integer array `coins` representing denominations, and an integer `amount`.
// Return the minimum number of coins required to make the given amount.
// If it is not possible, return -1.
//
// Example:
// Input: coins = [1, 2, 5], amount = 11
// Output: 3
// Explanation: The answer is 11 = 5 + 5 + 1.
//
// Approach (Unbounded Knapsack / DP):
// This is a classic unbounded knapsack problem because each coin can be taken multiple times.
//
// DP Definition:
// dp[i][j] = minimum number of coins required to make sum j using the first i coins.
//
// Initialization:
// - dp[0][j] = INF (cannot form positive amount with 0 coins)
// - dp[i][0] = 0  (0 amount requires 0 coins)
// - First row (i=1) filled manually because we can use unlimited copies of coin[0]
//
// Transition:
// For each coin i and amount j:
// - If coin[i-1] <= j, we have two options:
//     1. Exclude the coin → dp[i-1][j]
//     2. Include the coin  → 1 + dp[i][j - coin[i-1]]  (stay in same row because unlimited use)
// - Otherwise, we cannot take this coin → dp[i-1][j]
//
// Final Answer:
// - dp[n][amount] if it's not INF
// - Otherwise return -1
//
// Time Complexity: O(n * amount)
// Space Complexity: O(n * amount)

public class CoinChange322 {

    private int CoinChange(int[] coins, int amount) {
        int n = coins.length;

        // dp[i][j] = minimum coins to make amount j using first i coins
        int[][] dp = new int[n + 1][amount + 1];

        // Base case: 0 coins → impossible to form positive amount
        for (int j = 0; j <= amount; j++) {
            dp[0][j] = Integer.MAX_VALUE;
        }

        // Base case: amount 0 → requires 0 coins
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 0;
        }

        // Handle first coin separately (unbounded: repeated use allowed)
        for (int j = 1; j <= amount; j++) {
            if (j % coins[0] == 0) {
                dp[1][j] = j / coins[0];
            } else {
                dp[1][j] = Integer.MAX_VALUE;
            }
        }

        // Fill DP table for remaining coins
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= amount; j++) {

                // Option 1: Exclude current coin
                int exclude = dp[i - 1][j];

                // Option 2: Include current coin (only if j >= coins[i-1])
                int include = Integer.MAX_VALUE;
                if (coins[i - 1] <= j && dp[i][j - coins[i - 1]] != Integer.MAX_VALUE) {
                    include = 1 + dp[i][j - coins[i - 1]];
                }

                dp[i][j] = Math.min(exclude, include);
            }
        }

        return dp[n][amount] == Integer.MAX_VALUE ? -1 : dp[n][amount];
    }

    public static void main(String[] args) {
        CoinChange322 cc = new CoinChange322();
        int[] coins = {1, 2, 5};
        int amount = 11;
        int result = cc.CoinChange(coins, amount);
        System.out.println(result); // Expected Output: 3
    }
}
