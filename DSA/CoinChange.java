// 🪙 Coin Change Problem using Dynamic Programming
public class CoinChange {

    // Function to find the fewest number of coins needed to make up the given amount
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];

        // Fill dp array with a large value (unreachable state)
        for (int i = 1; i <= amount; i++) {
            dp[i] = amount + 1;
        }

        // Base case: 0 coins needed to make amount 0
        dp[0] = 0;

        // Compute dp[i] for all amounts up to the target
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        // If dp[amount] is still large, it means amount can't be made up
        return (dp[amount] > amount) ? -1 : dp[amount];
    }

    // 🔍 Test cases
    public static void main(String[] args) {
        System.out.println("Output: " + coinChange(new int[]{1, 2, 5}, 11)); // 3
        System.out.println("Output: " + coinChange(new int[]{2}, 3));        // -1
        System.out.println("Output: " + coinChange(new int[]{1}, 0));        // 0
        System.out.println("Output: " + coinChange(new int[]{186,419,83,408}, 6249)); // 20
    }
}
