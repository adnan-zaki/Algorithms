public class RegularExpressionMatching {

    static class Solution {
        public boolean isMatch(String s, String p) {
            if (s == null || p == null) {
                return false;
            }

            int m = s.length();
            int n = p.length();
            boolean[][] dp = new boolean[m + 1][n + 1];

            dp[0][0] = true;

            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[0][j] = dp[0][j - 2];
                }
            }

            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    char sChar = s.charAt(i - 1);
                    char pChar = p.charAt(j - 1);

                    if (pChar != '*') {
                        boolean currentCharsMatch = (sChar == pChar || pChar == '.');
                        dp[i][j] = dp[i - 1][j - 1] && currentCharsMatch;
                    } else {
                        char prevChar = p.charAt(j - 2);
                        
                        boolean matchZero = dp[i][j - 2];

                        boolean currentCharsMatch = (sChar == prevChar || prevChar == '.');
                        
                        boolean matchOneOrMore = currentCharsMatch && dp[i - 1][j];
                        
                        dp[i][j] = matchZero || matchOneOrMore;
                    }
                }
            }
            
            return dp[m][n];
        }
    }

    /**
     * Main method to test the solution.
     */
    public static void main(String[] args) {
        Solution sol = new Solution();

        System.out.println("s=\"aa\", p=\"a\" -> " + sol.isMatch("aa", "a"));
        System.out.println("s=\"aa\", p=\"a*\" -> " + sol.isMatch("aa", "a*"));
        System.out.println("s=\"ab\", p=\".*\" -> " + sol.isMatch("ab", ".*"));
        System.out.println("s=\"mississippi\", p=\"mis*is*p*.\" -> " + sol.isMatch("mississippi", "mis*is*p*."));
        System.out.println("s=\"mississippi\", p=\"mis*is*ip*.\" -> " + sol.isMatch("mississippi", "mis*is*ip*."));
        System.out.println("s=\"aab\", p=\"c*a*b\" -> " + sol.isMatch("aab", "c*a*b"));
        System.out.println("s=\"\", p=\".*\" -> " + sol.isMatch("", ".*"));
    }
}

