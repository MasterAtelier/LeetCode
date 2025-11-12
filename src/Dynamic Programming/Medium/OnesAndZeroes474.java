// Problem: Ones and Zeroes
// URL: https://leetcode.com/problems/ones-and-zeroes/
//
// Description:
// You are given an array of binary strings `strs` and two integers `m` and `n`.
// Each string `strs[i]` consists only of '0's and '1's.
// You can select any subset of these strings such that the total number of '0's ≤ m 
// and the total number of '1's ≤ n.
//
// Return the size of the largest possible subset satisfying the above condition.
//
// Example:
// Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
// Output: 4
// Explanation:
// The largest subset with at most 5 zeros and 3 ones is {"10", "0001", "1", "0"}.
//
// Approach (Top-Down Dynamic Programming with Memoization):
// 1. Preprocess each string to count its number of zeros and ones.
// 2. Use recursion with memoization to explore two choices for each string:
//    - Skip the current string.
//    - Take the current string (if it fits within remaining zero/one limits).
// 3. Use a HashMap with a custom Key (m, n, pos) to cache intermediate results.
// 4. Return the maximum subset size found.
//
// Time Complexity: O(L * m * n)
//   - L = number of strings, m and n are the given constraints.
// Space Complexity: O(L * m * n) for the recursion + memoization cache.

import java.util.HashMap;

public class OnesAndZeroes474 {

    // Helper class to store number of zeros and ones for each string
    private static class Pair {

        int zeros, ones;

        Pair(int zeros, int ones) {
            this.zeros = zeros;
            this.ones = ones;
        }
    }

    // Custom key class used for memoization (to uniquely identify subproblems)
    private static class Key {

        int m, n, pos; // remaining zeros, remaining ones, current index position

        Key(int m, int n, int pos) {
            this.m = m;
            this.n = n;
            this.pos = pos;
        }

        // Override equals() to ensure two keys with same state are treated as equal
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Key)) {
                return false;
            }
            Key k = (Key) o;
            return m == k.m && n == k.n && pos == k.pos;
        }

        // Good mixing hash function for small integer fields
        @Override
        public int hashCode() {
            return (m * 31 + n) * 31 + pos;
        }
    }

    // Recursive function to compute maximum subset size given constraints (m zeros, n ones)
    private int getMaxSubset(Pair[] count, int m, int n, int pos, HashMap<Key, Integer> memo) {
        // Base case: reached end of list
        if (pos == count.length) {
            return 0;
        }

        // Create key representing current state
        Key key = new Key(m, n, pos);

        // Return cached result if already computed
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Option 1: Skip current string
        int skip = getMaxSubset(count, m, n, pos + 1, memo);

        // Option 2: Take current string (if within available zeros and ones)
        int take = 0;
        if (count[pos].zeros <= m && count[pos].ones <= n) {
            take = 1 + getMaxSubset(count, m - count[pos].zeros, n - count[pos].ones, pos + 1, memo);
        }

        // Store and return maximum of the two choices
        int res = Math.max(skip, take);
        memo.put(key, res);
        return res;
    }

    // Main method to compute maximum subset of binary strings within given constraints
    public int findMaxForm(String[] strs, int m, int n) {
        Pair[] count = new Pair[strs.length];

        // Precompute number of zeros and ones for each string
        for (int i = 0; i < strs.length; i++) {
            int zeros = 0, ones = 0;
            for (char c : strs[i].toCharArray()) {
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            count[i] = new Pair(zeros, ones);
        }

        // Start recursive search with memoization
        return getMaxSubset(count, m, n, 0, new HashMap<>());
    }

    public static void main(String[] args) {
        OnesAndZeroes474 solution = new OnesAndZeroes474();
        String[] strs = {"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;
        int result = solution.findMaxForm(strs, m, n);
        System.out.println("Maximum subset size: " + result); // Output: 4
    }
}
