import java.util.*;

class Solution {
    /**
     * Finds the minimum time to swim from top-left to bottom-right
     * Time Complexity: O(n² log n²) due to priority queue operations
     * Space Complexity: O(n²) for visited array and priority queue
     */
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        
        // Min-heap: stores [elevation, row, col]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Track visited cells
        boolean[][] visited = new boolean[n][n];
        
        // Directions: up, down, left, right
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // Start from top-left cell
        pq.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0] = true;
        
        // Track maximum elevation encountered (minimum time needed)
        int maxElevation = grid[0][0];
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int elevation = curr[0];
            int row = curr[1];
            int col = curr[2];
            
            // Update maximum elevation along the path
            maxElevation = Math.max(maxElevation, elevation);
            
            // If we reached the destination, return the time
            if (row == n - 1 && col == n - 1) {
                return maxElevation;
            }
            
            // Explore all 4 neighbors
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check bounds and if already visited
                if (newRow >= 0 && newRow < n && 
                    newCol >= 0 && newCol < n && 
                    !visited[newRow][newCol]) {
                    
                    visited[newRow][newCol] = true;
                    pq.offer(new int[]{grid[newRow][newCol], newRow, newCol});
                }
            }
        }
        
        // Should never reach here given valid input
        return -1;
    }
    
    /**
     * Test the solution with provided examples
     */
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Test Case 1
        int[][] grid1 = {{0, 2}, {1, 3}};
        System.out.println("Test Case 1:");
        System.out.println("Grid: [[0,2],[1,3]]");
        System.out.println("Output: " + solution.swimInWater(grid1));
        System.out.println("Expected: 3");
        System.out.println();
        
        // Test Case 2
        int[][] grid2 = {
            {0, 1, 2, 3, 4},
            {24, 23, 22, 21, 5},
            {12, 13, 14, 15, 16},
            {11, 17, 18, 19, 20},
            {10, 9, 8, 7, 6}
        };
        System.out.println("Test Case 2:");
        System.out.println("Grid: 5x5 spiral");
        System.out.println("Output: " + solution.swimInWater(grid2));
        System.out.println("Expected: 16");
        System.out.println();
        
        // Test Case 3: Single cell
        int[][] grid3 = {{0}};
        System.out.println("Test Case 3:");
        System.out.println("Grid: [[0]]");
        System.out.println("Output: " + solution.swimInWater(grid3));
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test Case 4: 3x3 grid
        int[][] grid4 = {
            {3, 2, 1},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test Case 4:");
        System.out.println("Grid: [[3,2,1],[4,5,6],[7,8,9]]");
        System.out.println("Output: " + solution.swimInWater(grid4));
        System.out.println("Expected: 9");
    }
}

/**
 * ALGORITHM EXPLANATION:
 * 
 * Key Insight:
 * - The problem is equivalent to finding the path from (0,0) to (n-1,n-1)
 *   where the maximum elevation along the path is minimized.
 * - This is a modified shortest path problem using Dijkstra's algorithm.
 * 
 * Approach:
 * 1. Use a min-heap (priority queue) ordered by elevation values
 * 2. Start from (0,0) and explore neighbors with lowest elevation first
 * 3. Track the maximum elevation encountered on the current path
 * 4. When we reach (n-1, n-1), the maximum elevation is our answer
 * 
 * Why this works:
 * - By always exploring the cell with lowest elevation first, we ensure
 *   we find the path that minimizes the maximum elevation
 * - The greedy choice at each step leads to the optimal solution
 * 
 * Example walkthrough (grid1 = [[0,2],[1,3]]):
 * - Start at (0,0) with elevation 0
 * - Can go to (0,1) elevation 2 or (1,0) elevation 1
 * - Choose (1,0) first (lower elevation)
 * - From (1,0), go to (1,1) elevation 3
 * - But we also explore (0,1) elevation 2 from start
 * - Path through (0,1) to (1,1): max(0,2,3) = 3
 * - Answer: 3
 */
