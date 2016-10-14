package amazon.interview;


/**
 * Assumptions:
 * 1. Exists a directed graph represented by adjacency lists as the DirectedGraph class
 * 2. A vertex in the graph correspond to an integer from 0 to N
 * 3. DirectedGraph "vertices" method returns the number of vertices
 * 4. DirectedGraph "adjacentVertices(int v)" method return the List of adjacencies for v
 * 5. After perform the Depth First Search from the source, the method "hasRouteTo(int v)" resolves if there is a route between two nodes
 */
public class DirectedDFS {
    private boolean[] marked;

    public DirectedDFS(DirectedGraph g, int source) {
        marked = new boolean[g.vertices()];
        dfs(g, source);
    }

    private void dfs(DirectedGraph g, int source) {
        marked[source] = true;
        for (int v : g.adjacentVertices(source)) {
            if (!marked[v]) {
                dfs(g, v);
            }
        }
    }

    public boolean hasRouteTo(int v) {
        return marked[v];
    }
}