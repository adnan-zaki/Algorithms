//Depth First Search (DFS) implementation in java
import java.util.*;

public class DFS {
    private int V;
    private List<Integer>[] adj;

    // Constructor
    @SuppressWarnings("unchecked") // to suppress generic array warning safely
    DFS(int v) {
        V = v;
        adj = (List<Integer>[]) new ArrayList[v];
        for (int i = 0; i < v; i++)
            adj[i] = new ArrayList<>();
    }

    // Add an edge (undirected)
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // Recursive DFS function
    void dfsUtil(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");

        for (int n : adj[v]) {
            if (!visited[n])
                dfsUtil(n, visited);
        }
    }

    // DFS traversal from a given node
    void dfs(int start) {
        boolean[] visited = new boolean[V];
        System.out.println("DFS starting from vertex " + start + ":");
        dfsUtil(start, visited);
    }

    // Main function
    public static void main(String[] args) {
        DFS graph = new DFS(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        graph.dfs(0);
    }
}
