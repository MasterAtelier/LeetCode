
/**
 * Problem: Online Stock Span
 * URL: https://leetcode.com/problems/online-stock-span/
 *
 * Description:
 * Design an algorithm that collects daily stock prices and returns the "span" of the stock's price for each day.
 * The span of the stock's price today is defined as the number of consecutive days (up to today)
 * the price of the stock has been less than or equal to today's price.
 *
 * Example:
 * Input:  ["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
 *         [[], [100], [80], [60], [70], [60], [75], [85]]
 * Output: [null, 1, 1, 1, 2, 1, 4, 6]
 *
 * Explanation:
 * - Day 1: price = 100 → span = 1
 * - Day 2: price = 80  → span = 1
 * - Day 3: price = 60  → span = 1
 * - Day 4: price = 70  → span = 2 (70 > 60)
 * - Day 5: price = 60  → span = 1
 * - Day 6: price = 75  → span = 4 (75 >= 60, 70, 60)
 * - Day 7: price = 85  → span = 6
 *
 * Approach:
 * 1. Use a monotonic decreasing stack to store pairs (price, index).
 * 2. For each new price:
 *    - Pop all previous prices from the stack that are less than or equal to the current price.
 *    - The next greater price to the left (or none if stack is empty) determines the start of the span.
 *    - Compute span = current_index - previous_greater_price_index.
 * 3. Push the current (price, index) pair to the stack.
 *
 * Time Complexity: O(1) amortized per operation.
 *   Each price is pushed and popped at most once.
 *
 * Space Complexity: O(N)
 *   Stack stores up to N pairs.
 */
import java.util.Stack;

public class OnlineStockSpan901 {

    // Stack stores pairs: [price, index]
    private Stack<Integer[]> stack;
    private int idx;

    public OnlineStockSpan901() {
        stack = new Stack<>();
        idx = -1;  // represents the index of the current day
    }

    public int next(int price) {
        idx += 1;

        // Step 1: Remove all smaller or equal prices from previous days
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            stack.pop();
        }

        // Step 2: Calculate span length
        // If stack is empty, all previous prices are <= current price → span = idx - (-1)
        // Else, span = distance from last higher price
        int ans = idx - (stack.isEmpty() ? -1 : stack.peek()[1]);

        // Step 3: Push current day's price and index
        stack.push(new Integer[]{price, idx});

        return ans;
    }

    // ---------- Main Method for Testing ----------
    public static void main(String[] args) {
        OnlineStockSpan901 stockSpanner = new OnlineStockSpan901();

        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        for (int price : prices) {
            System.out.println("Price: " + price + " -> Span: " + stockSpanner.next(price));
        }
    }
}
