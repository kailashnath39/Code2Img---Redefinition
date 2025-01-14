public class K113 {
    public void dijkstraT1(int graph[][], int src) {
        int V = graph.length; int dist[] = new int[V]; boolean visited[] = new boolean[V]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src] = 0;
        for (int count = 0; count < V - 1; count++) { int u = minDistance(dist, visited); visited[u] = true; for (int v = 0; v < V; v++) {
            if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) dist[v] = dist[u] + graph[u][v];
        }}
        printSolution(dist);
    }

} 
