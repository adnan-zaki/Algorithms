public class BurstBalloons {

    static class Solution {
        public int maxCoins(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                return 0;
            }

            int[] padded = new int[n + 2];
            padded[0] = 1;
            padded[n + 1] = 1;
            System.arraycopy(nums, 0, padded, 1, n);
            int N = n + 2;

            int[][] dp = new int[N][N];

            for (int len = 2; len < N; len++) {
                for (int i = 0; i <= N - 1 - len; i++) {
                    int j = i + len;
                    
                    dp[i][j] = 0;

                    for (int k = i + 1; k < j; k++) {
                        int coins = padded[i] * padded[k] * padded[j];
                        int subProblems = dp[i][k] + dp[k][j];
                        dp[i][j] = Math.max(dp[i][j], coins + subProblems);
                    }
                }
            }
            return dp[0][N - 1];
        }
    }

    /**
     * Main method to test the solution.
     */
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] nums1 = {3, 1, 5, 8};
        System.out.println("Input: [3, 1, 5, 8]");
        System.out.println("Output: " + sol.maxCoins(nums1)); // Expected: 167

        int[] nums2 = {9};
        System.out.println("\nInput: [9]");
        System.out.println("Output: " + sol.maxCoins(nums2)); // Expected: 9

        int[] nums3 = {1, 5};
        System.out.println("\nInput: [1, 5]");
        System.out.println("Output: " + sol.maxCoins(nums3)); // Expected: 10
    }
}

