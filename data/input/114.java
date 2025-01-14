public class K114 {
    public void shortestPath(int adjacencyMatrix[][], int source) {
        int vertices = adjacencyMatrix.length;
        int distances[] = new int[vertices];
        boolean isVisited[] = new boolean[vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        for (int count = 0; count < vertices - 1; count++) {
            int nearestVertex = minDistance(distances, isVisited);
            isVisited[nearestVertex] = true;

            for (int adjacentVertex = 0; adjacentVertex < vertices; adjacentVertex++) {
                if (!isVisited[adjacentVertex] && adjacencyMatrix[nearestVertex][adjacentVertex] != 0 &&
                        distances[nearestVertex] != Integer.MAX_VALUE &&
                        distances[nearestVertex] + adjacencyMatrix[nearestVertex][adjacentVertex] < distances[adjacentVertex]) {
                    distances[adjacentVertex] = distances[nearestVertex] + adjacencyMatrix[nearestVertex][adjacentVertex];
                }
            }
        }
        printSolution(distances);
    }

} 
