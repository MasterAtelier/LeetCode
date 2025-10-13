
/**
 * Problem: Asteroid Collision
 * URL: https://leetcode.com/problems/asteroid-collision/
 *
 * Description:
 * We are given an array of integers representing asteroids moving along a line.
 * Each asteroid's absolute value represents its size, and its sign represents its direction:
 *  - Positive value → moving to the right.
 *  - Negative value → moving to the left.
 *
 * When two asteroids collide:
 *  - The smaller one explodes.
 *  - If both are the same size, both explode.
 *  - Asteroids moving in the same direction never collide.
 *
 * Return the state of the asteroids after all collisions.
 *
 * Example:
 * Input:  [5, 10, -5]
 * Output: [5, 10]
 *
 * Explanation:
 *  - The 10 and -5 collide → 10 survives because |10| > |–5|.
 *
 * Approach:
 * 1. Use a stack to simulate asteroid collisions.
 * 2. Push each asteroid to the stack:
 *    - If the current asteroid is moving right, push it directly.
 *    - If it's moving left, check for collisions:
 *        • Keep popping from the stack while the top is moving right and smaller than the current asteroid.
 *        • If they are equal, pop and destroy both.
 *        • If top is larger, the current asteroid gets destroyed.
 * 3. After processing all asteroids, the stack represents the surviving ones in order.
 *
 * Time Complexity: O(N)
 *   Each asteroid is pushed and popped at most once.
 *
 * Space Complexity: O(N)
 *   The stack can contain up to N asteroids.
 */
import java.util.Stack;

public class AsteroidCollision735 {

    public int[] asteroidCollision(int[] asteroids) {
        int n = asteroids.length;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            boolean destroyed = false;

            // Process possible collisions
            while (!stack.isEmpty() && asteroids[i] < 0 && stack.peek() > 0) {
                if (stack.peek() < -asteroids[i]) {
                    // Current asteroid destroys smaller one moving right
                    stack.pop();
                } else if (stack.peek() == -asteroids[i]) {
                    // Both asteroids destroy each other
                    stack.pop();
                    destroyed = true;
                    break;
                } else {
                    // Current asteroid is destroyed by a larger one
                    destroyed = true;
                    break;
                }
            }

            // If asteroid survives collision, push it to stack
            if (!destroyed) {
                stack.push(asteroids[i]);
            }
        }

        // Convert stack to result array (in correct order)
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }

        return res;
    }

    // ---------- Main Method for Testing ----------
    public static void main(String[] args) {
        AsteroidCollision735 solver = new AsteroidCollision735();

        int[] asteroids = {5, 10, -5};
        int[] result = solver.asteroidCollision(asteroids);

        System.out.print("Resulting Asteroids: ");
        for (int val : result) {
            System.out.print(val + " ");
        }
    }
}
