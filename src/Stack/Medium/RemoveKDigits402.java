
/**
 * Problem: Remove K Digits
 * URL: https://leetcode.com/problems/remove-k-digits/
 *
 * Description:
 * Given a non-negative integer num represented as a string and an integer k,
 * remove k digits from the number so that the new number is the smallest possible.
 *
 * Example:
 * Input:  num = "1432219", k = 3
 * Output: "1219"
 *
 * Explanation:
 * - Remove digits '4', '3', and '2' → smallest possible number is "1219".
 *
 * Approach:
 * 1. Use a stack to construct the smallest possible number:
 *    - Iterate over digits of the number.
 *    - While the current digit is smaller than the top of the stack and we can still remove digits (k > 0),
 *      pop from the stack.
 *    - Push the current digit to the stack.
 * 2. If k > 0 after traversal, remove the remaining k digits from the end (stack top).
 * 3. Build the result from the stack and remove leading zeros.
 * 4. If result is empty, return "0".
 *
 * Time Complexity: O(N)
 *   Each digit is pushed and popped at most once.
 *
 * Space Complexity: O(N)
 *   Stack stores up to N digits.
 */
import java.util.Stack;

public class RemoveKDigits402 {

    public String removeKdigits(String num, int k) {
        int n = num.length();

        // Edge case: remove all digits
        if (k == n) {
            return "0";
        }

        Stack<Character> stack = new Stack<>();

        // Iterate through all digits
        for (int i = 0; i < n; i++) {
            char current = num.charAt(i);

            // Maintain increasing order in the stack (monotonic stack)
            while (!stack.isEmpty() && k > 0 && stack.peek() > current) {
                stack.pop();
                k--;
            }

            stack.push(current);
        }

        // If k still remains, remove from the end (largest digits)
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }

        // Build the result from stack
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        // Reverse to restore correct order
        String ans = sb.reverse().toString();

        // Remove leading zeros
        ans = ans.replaceFirst("^0+", "");

        // If result is empty, return "0"
        return ans.isEmpty() ? "0" : ans;
    }

    // ---------- Main Method for Testing ----------
    public static void main(String[] args) {
        RemoveKDigits402 solver = new RemoveKDigits402();

        String num = "1432219";
        int k = 3;

        String result = solver.removeKdigits(num, k);
        System.out.println("Result after removing " + k + " digits: " + result);
    }
}
