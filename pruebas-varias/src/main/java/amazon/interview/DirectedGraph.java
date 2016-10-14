package amazon.interview;


import java.util.ArrayList;
import java.util.List;

public class DirectedGraph {
    private final int vertices;
    private int edges;
    private List<Integer>[] adjacency;

    public DirectedGraph(int vertices) {
        this.vertices = vertices;
        this.edges = 0;
        adjacency = (List<Integer>[]) new List[vertices];
        for (int v = 0; v < vertices; v++)
            adjacency[v] = new ArrayList<Integer>();
    }

    public int vertices() {
        return vertices;
    }

    public int edges() {
        return edges;
    }

    public void addEdge(int v, int w) {
        adjacency[v].add(w);
        edges++;
    }

    public Iterable<Integer> adjacentVertices(int v) {
        return adjacency[v];
    }
}
