package org.example;

import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Data {

    public int N = 12;
    public int P = 4;
    public int H = N / P;
    public int[][] MO = new int[N][N];
    public int[][] MR = new int[N][N];
    public int[] B = new int[N];
    public int[] Z = new int[N];
    public int[] R = new int[N];
    public int[] X = new int[N];
    public int d = 1;

    Semaphore S0 = new Semaphore(1);
    Semaphore S1 = new Semaphore(0);
    Semaphore S2 = new Semaphore(0);
    Semaphore S3 = new Semaphore(0);
    Semaphore S4 = new Semaphore(0);
    CyclicBarrier B1 = new CyclicBarrier(4);
    CyclicBarrier B2 = new CyclicBarrier(4);
    public AtomicInteger a = new AtomicInteger(0);

    public synchronized int CS1() {
        return a.intValue();
    }

    public int [] fillVector(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 1;
        }
        return vector;
    }

    public int[][] fillMatrix(int[][] matrix) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1;
            }
        }
        return matrix;
    }

    public static int[][] getSubmatrix(int[][] matrix, int start, int end) {
        if (start < 0 || start >= matrix[0].length || end <= start || end > matrix[0].length) {
            throw new IllegalArgumentException("Invalid column indices");
        }

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

    public static int[] getSubvector(int[] vector, int start, int end) {
        if (start < 0 || start >= vector.length || end <= start || end > vector.length) {
            throw new IllegalArgumentException("Invalid indices");
        }

        int size = end - start;
        int[] subvector = new int[size];

        for (int i = start; i < end; i++) {
            subvector[i - start] = vector[i];
        }

        return subvector;
    }

    public static int multiplyVectors(int[] vector1, int[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }

        int result = 0;

        for (int i = 0; i < vector1.length; i++) {
            result += vector1[i] * vector2[i];
        }

        return result;
    }

    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2, int start, int end) {
        int rowsMatrix1 = matrix1.length;

        int[][] result = new int[rowsMatrix1][end - start];

        for (int i = 0; i < rowsMatrix1; i++) {
            for (int j = 0; j < end-start; j++) {
                for (int k = 0; k < rowsMatrix1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    public static int[] multiplyVectorByMatrix(int[] vector, int[][] matrix) {

        int[] result = new int[matrix.length];

        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i] += matrix[j][i] * vector[j];
            }

        }
        return result;
    }

    public static int[] calculateXh(int[] C, int[] Z, int a, int d) {
        int length = Math.min(C.length, Z.length);
        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            result[i] = a * d * Z[i] + a * C[i];
        }

        return result;
    }
}
