public class K117 {
    public void bellmanFord(int graph[][], int src) {
        int V = graph.length;
        int E = 0;
        for (int[] edges : graph) {
            for (int edge : edges) {
                if (edge != 0) E++;
            }
        }

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 1; i < V; i++) {
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                            dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }
        }
        printSolution(dist);
    }

} 
