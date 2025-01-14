// import java.util.*;
//
// public class SingleSourceShortestPath {
//
//     // T1 Clone 1: Dijkstra's algorithm implementation for single source shortest path
//     public void dijkstra(int graph[][], int src) {
//         int V = graph.length;
//         int dist[] = new int[V];
//         boolean visited[] = new boolean[V];
//
//         Arrays.fill(dist, Integer.MAX_VALUE);
//         dist[src] = 0;
//
//         for (int count = 0; count < V - 1; count++) {
//             int u = minDistance(dist, visited);
//             visited[u] = true;
//
//             for (int v = 0; v < V; v++) {
//                 if (!visited[v] && graph[u][v] != 0 &&
//                         dist[u] != Integer.MAX_VALUE &&
//                         dist[u] + graph[u][v] < dist[v]) {
//                     dist[v] = dist[u] + graph[u][v];
//                 }
//             }
//         }
//         printSolution(dist);
//     }
//
//     private int minDistance(int[] dist, boolean[] visited) {
//         int min = Integer.MAX_VALUE, minIndex = -1;
//
//         for (int v = 0; v < dist.length; v++) {
//             if (!visited[v] && dist[v] <= min) {
//                 min = dist[v];
//                 minIndex = v;
//             }
//         }
//         return minIndex;
//     }
//
//     private void printSolution(int[] dist) {
//         System.out.println("Vertex\tDistance from Source");
//         for (int i = 0; i < dist.length; i++) {
//             System.out.println(i + "\t" + dist[i]);
//         }
//     }
//
//     // T1 Clone 2: Dijkstra's algorithm (Identical except for comments and spacing)
//     public void dijkstraT1(int graph[][], int src) {
//         int V = graph.length; int dist[] = new int[V]; boolean visited[] = new boolean[V]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src] = 0;
//         for (int count = 0; count < V - 1; count++) { int u = minDistance(dist, visited); visited[u] = true; for (int v = 0; v < V; v++) {
//             if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) dist[v] = dist[u] + graph[u][v];
//         }}
//         printSolution(dist);
//     }
//
//     // T2 Clone: Dijkstra with renamed identifiers
//     public void shortestPath(int adjacencyMatrix[][], int source) {
//         int vertices = adjacencyMatrix.length;
//         int distances[] = new int[vertices];
//         boolean isVisited[] = new boolean[vertices];
//
//         Arrays.fill(distances, Integer.MAX_VALUE);
//         distances[source] = 0;
//
//         for (int count = 0; count < vertices - 1; count++) {
//             int nearestVertex = minDistance(distances, isVisited);
//             isVisited[nearestVertex] = true;
//
//             for (int adjacentVertex = 0; adjacentVertex < vertices; adjacentVertex++) {
//                 if (!isVisited[adjacentVertex] && adjacencyMatrix[nearestVertex][adjacentVertex] != 0 &&
//                         distances[nearestVertex] != Integer.MAX_VALUE &&
//                         distances[nearestVertex] + adjacencyMatrix[nearestVertex][adjacentVertex] < distances[adjacentVertex]) {
//                     distances[adjacentVertex] = distances[nearestVertex] + adjacencyMatrix[nearestVertex][adjacentVertex];
//                 }
//             }
//         }
//         printSolution(distances);
//     }
//
//     // T3 Clone: Modified Dijkstra with additional functionality
//     public void dijkstraWithPathTracking(int graph[][], int src) {
//         int V = graph.length;
//         int dist[] = new int[V];
//         boolean visited[] = new boolean[V];
//         int[] parent = new int[V];
//
//         Arrays.fill(dist, Integer.MAX_VALUE);
//         Arrays.fill(parent, -1);
//         dist[src] = 0;
//
//         for (int count = 0; count < V - 1; count++) {
//             int u = minDistance(dist, visited);
//             visited[u] = true;
//
//             for (int v = 0; v < V; v++) {
//                 if (!visited[v] && graph[u][v] != 0 &&
//                         dist[u] != Integer.MAX_VALUE &&
//                         dist[u] + graph[u][v] < dist[v]) {
//                     dist[v] = dist[u] + graph[u][v];
//                     parent[v] = u;
//                 }
//             }
//         }
//         System.out.println("Vertex\tDistance\tPath");
//         for (int i = 0; i < dist.length; i++) {
//             System.out.print(i + "\t" + dist[i] + "\t");
//             printPath(i, parent);
//             System.out.println();
//         }
//     }
//
//     private void printPath(int current, int[] parent) {
//         if (current == -1) return;
//         printPath(parent[current], parent);
//         System.out.print(current + " ");
//     }
//
//     // T4 Clone: Bellman-Ford Algorithm (Different algorithm, similar functionality)
//     public void bellmanFord(int graph[][], int src) {
//         int V = graph.length;
//         int E = 0;
//         for (int[] edges : graph) {
//             for (int edge : edges) {
//                 if (edge != 0) E++;
//             }
//         }
//
//         int[] dist = new int[V];
//         Arrays.fill(dist, Integer.MAX_VALUE);
//         dist[src] = 0;
//
//         for (int i = 1; i < V; i++) {
//             for (int u = 0; u < V; u++) {
//                 for (int v = 0; v < V; v++) {
//                     if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
//                             dist[u] + graph[u][v] < dist[v]) {
//                         dist[v] = dist[u] + graph[u][v];
//                     }
//                 }
//             }
//         }
//         printSolution(dist);
//     }
//
//
// //    dis similar functions: not similar to above functions
// public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
//     if (matrixA[0].length != matrixB.length) {
//         throw new IllegalArgumentException("Number of columns in Matrix A must match the number of rows in Matrix B");
//     }
//
//     int m = matrixA.length;
//     int n = matrixB.length;
//     int p = matrixB[0].length;
//     int[][] resultMatrix = new int[m][p];
//
//     for (int i = 0; i < m; i++) {
//         for (int j = 0; j < p; j++) {
//             for (int k = 0; k < n; k++) {
//                 resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
//             }
//         }
//     }
//     return resultMatrix;
// }
//
//     /**
//      * Prints the matrix to the standard output.
//      * @param matrix The matrix to print.
//      */
//     public static void printMatrix(int[][] matrix) {
//         for (int[] row : matrix) {
//             for (int value : row) {
//                 System.out.print(value + " ");
//             }
//             System.out.println();
//         }
//     }
//
// }
//