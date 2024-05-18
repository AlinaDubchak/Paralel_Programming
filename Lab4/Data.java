package org.example;

public class Data {
    public int N = 8; 
    public int P = 4;
    public int H = N / P; 
    public int e;
    public int[] Z = new int[N];
    public int[] X = new int[N];
    public int[] B = new int[N];
    public int[] R = new int[N]; 
    public int[][] MV = new int[N][N];
    public int[][] MC = new int[N][N];
    public int[][] MM = new int[N][N];

    public Synchronized_Monitor Synchronized_Monitor = new Synchronized_Monitor();
    public Monitor_a Monitor_a = new Monitor_a();
    public Monitor_e Monitor_e = new Monitor_e();

    // Отримання максимального значення вектора
    public static int getMaxValueVector(int[] vector) {
        int max = vector[0];
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > max) {
                max = vector[i];
            }
        }
        return max;
    }

    // Множення двох матриць
    public static int[][] multiplyTwoMatrices(int[][] matrix1, int[][] matrix2, int start, int end) {
        int rowsMatrix1 = matrix1.length;


        int[][] result = new int[rowsMatrix1][end - start];


        for (int i = 0; i < rowsMatrix1; i++) {
            for (int j = 0; j < end - start; j++) {
                for (int k = 0; k < rowsMatrix1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }


        return result;
    }


    // Множення вектора на число
    public static int[] multiplyVectorByNumber(int[] vector, int number) {
        int[] result = new int[vector.length];

        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * number;
        }

        return result;
    }

    // Множення матриці на вектор
    public static int[] multiplyMatrixByVector(int[][] matrix, int[] vector) {
        int[] result = new int[matrix.length];


        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i] += matrix[j][i] * vector[j];
            }


        }
        return result;

    }

    // Отримання підматриці
    public static int[][] getSubmatrix(int[][] matrix, int start, int end) {
        int rows = matrix.length;
        int columns = end - start;

        int[][] submatrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = start; j < end; j++) {
                submatrix[i][j - start] = matrix[i][j];
            }
        }

        return submatrix;
    }

    // Додавання двох векторів
    public static int[] addTwoVectors(int[] vector1, int[] vector2) {
        int length = vector1.length;
        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            result[i] = vector1[i] + vector2[i];
        }

        return result;
    }

    // Вставлення підвектора вектору
    public static void insertSubvectorIntoVector(Data data, int[] subv, int startPos, int endPos) {
        for (int i = startPos, j = 0; i < endPos; i++, j++) {
            data.R[i] = subv[j];
        }
    }

    // Отримання підвектора
    public static int[] getSubvector(int[] sourceVector, int startPos, int endPos) {
        int size = endPos - startPos;
        int[] subvector = new int[size];

        for (int i = startPos; i < endPos; i++) {
            subvector[i - startPos] = sourceVector[i];
        }
        return subvector;
    }

    // Виведення вектора
    public static void printVector(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    // Обчислення Rн = a*(B*MVн) + e*Xн*(MMн*MC)
    public static int[] calculateResult(int a, int[] B, int[][] MV, int e, int[] X, int[][] MM, int[][] MC, int start, int end) {
        int[] res1 = multiplyMatrixByVector(MV, B);
        int[][] res2 = multiplyTwoMatrices(MM, MC, start, end);
        int [] res3 =  multiplyMatrixByVector(res2, X);
        int [] a_res1_h = multiplyVectorByNumber(res1, a);
        int[] e_res3_h = multiplyVectorByNumber(res3, e);
        return addTwoVectors(a_res1_h, e_res3_h);
    }

}
