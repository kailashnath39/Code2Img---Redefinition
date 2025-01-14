public class K115 {
    public void dijkstraWithPathTracking(int graph[][], int src) {
        int V = graph.length;
        int dist[] = new int[V];
        boolean visited[] = new boolean[V];
        int[] parent = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != 0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }
        System.out.println("Vertex\tDistance\tPath");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(i + "\t" + dist[i] + "\t");
            printPath(i, parent);
            System.out.println();
        }
    }

} 
