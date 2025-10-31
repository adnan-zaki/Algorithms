import java.util.PriorityQueue;

public class SwimInRisingWater {

    /**
     * Finds the minimum time to swim from top-left to bottom-right
     * using BFS with a Min-Heap (Dijkstra-like approach)
     *
     * @param grid n x n integer grid representing elevations
     * @return minimum time to reach bottom-right cell
     */
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];

        // Min-Heap: each element is {elevation, row, col}
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        minHeap.offer(new int[]{grid[0][0], 0, 0});
        visited[0][0] = true;

        int time = 0; // max elevation encountered along the path

        // Directions: down, up, right, left
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int elevation = curr[0];
            int r = curr[1];
            int c = curr[2];

            // Update the maximum elevation seen so far
            time = Math.max(time, elevation);

            // If we've reached the bottom-right cell, return the time
            if (r == n - 1 && c == n - 1) {
                return time;
            }

            // Explore all 4 neighboring cells
            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                // Only consider valid, unvisited cells
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    minHeap.offer(new int[]{grid[nr][nc], nr, nc});
                }
            }
        }

        return -1; // should never reach here for valid input
    }

    /*
    Example Test Cases (no hard-coded execution):

    1. Input: [[0,2],[1,3]]
       Output: 3

    2. Input: [
           [0,1,2,3,4],
           [24,23,22,21,5],
           [12,13,14,15,6],
           [11,17,18,19,7],
           [10,9,8,20,16]
       ]
       Output: 16

    3. Input: [[0]]
       Output: 0
    */
}
