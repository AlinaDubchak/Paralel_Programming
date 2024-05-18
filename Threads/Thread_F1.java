package org.example;

import static org.example.Data.*;

public class Thread_F1 extends Thread {
    private final int size;
    private final int value;
    private final int res;


    public Thread_F1(int size, int value, int res) {
        this.size = size;
        this.value = value;
        this.res = res;
    }

    @Override
    public void run() {

        System.out.println("T1 is started!");
        Data data = new Data();

        int[] vectorAB = new int[size];
        int[] vectorA, vectorB, vectorC;
        int[][] matrixMA, matrixME;
        String vector_A = "vector A", vector_B = "vector B", vector_C = "vector C", matrix_MA = "matrix MA", matrix_ME = "matrix ME";

        if (size >= 1000) {
            String fileNameA = "vectorA.txt";
            String fileNameB = "vectorB.txt";
            String fileNameC = "vectorC.txt";

            String fileNameMA = "matrixMA.txt";
            String fileNameME = "matrixME.txt";

            createFileWithVector(fileNameA, size, value, res);
            createFileWithVector(fileNameB, size, value, res);
            createFileWithVector(fileNameC, size, value, res);

            createFileWithMatrix(fileNameMA, size, value, res);
            createFileWithMatrix(fileNameME, size, value, res);

            vectorA = readVectorFromFile(fileNameA);
            vectorB = readVectorFromFile(fileNameB);
            vectorC = readVectorFromFile(fileNameC);

            matrixMA = readMatrixFromFile(fileNameMA);
            matrixME = readMatrixFromFile(fileNameME);
        } else {
            vectorA = data.initializeVector(size, value, vector_A);
            vectorB = data.initializeVector(size, value,vector_B );
            vectorC = data.initializeVector(size, value, vector_C);

            matrixMA = data.initializeMatrix(size, value, matrix_MA);
            matrixME = data.initializeMatrix(size, value, matrix_ME);
        }
        // A + B
        for (int i = 0; i < size; i++) {
            vectorAB[i] = vectorA[i] + vectorB[i];
        }

        //  matrixMA * matrixME
        int[][] resultMatrix = data.multiplyMatrices(matrixMA, matrixME);

        // C * (matrixMA * matrixME)
        int[] resultVector = data.multiplyVectorMatrix(vectorC, resultMatrix);

        // ((A + B)* (C *(MA*ME)))
        int finalResultF1 = data.multiplyVectors(vectorAB, resultVector);

        if (size >= 1000) {
            writeScalarToFile("FinalF1.txt", finalResultF1);
        } else {
            System.out.println("Final result d = " + finalResultF1);
        }

        System.out.println("T1 is finished!");
    }

}
