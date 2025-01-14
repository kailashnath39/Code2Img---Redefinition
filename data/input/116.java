public class K116 {
    private void printPath(int current, int[] parent) {
        if (current == -1) return;
        printPath(parent[current], parent);
        System.out.print(current + " ");
    }

} 
