public class K118 {
    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        if (matrixA[0].length != matrixB.length) {
            throw new IllegalArgumentException("Number of columns in Matrix A must match the number of rows in Matrix B");
        }
   
        int m = matrixA.length;
        int n = matrixB.length;
        int p = matrixB[0].length;
        int[][] resultMatrix = new int[m][p];
   
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return resultMatrix;
    }
   
} 
