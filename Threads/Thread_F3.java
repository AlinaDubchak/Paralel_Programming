package org.example;

import java.util.Arrays;

import static org.example.Data.*;
import static org.example.Data.readMatrixFromFile;

public class Thread_F3 extends Thread {
    private final int size;
    private final int value;
    private final int res;


    public Thread_F3(int size, int value, int res) {
        this.size = size;
        this.value = value;
        this.res = res;
    }

    @Override
    public void run() {
        System.out.println("T3 is started!");
        Data data = new Data();

        int [] vectorO, vectorP, vectorV;
        int[] vectorOPV = new int[size];
        int[][] matrixMR, matrixMS;
        String vector_O = "vector O", vector_P = "vector P", vector_V = "vector V", matrix_MR = "matrix MR", matrix_MS = "matrix MS";

        if (size >=1000){
            String fileNameO = "vectorO.txt";
            String fileNameP = "vectorP.txt";
            String fileNameV = "vectorV.txt";

            String fileNameMR = "matrixMR.txt";
            String fileNameMS = "matrixMS.txt";

            createFileWithVector(fileNameO, size, value, res);
            createFileWithVector(fileNameP, size, value, res);
            createFileWithVector(fileNameV, size, value, res);

            createFileWithMatrix(fileNameMR, size, value, res);
            createFileWithMatrix(fileNameMS, size, value, res);

            vectorO = readVectorFromFile(fileNameO);
            vectorP = readVectorFromFile(fileNameP);
            vectorV = readVectorFromFile(fileNameV);

            matrixMR = readMatrixFromFile(fileNameMR);
            matrixMS = readMatrixFromFile(fileNameMS);
        } else {
            vectorO = data.initializeVector(size, value,vector_O );
            vectorP = data.initializeVector(size, value, vector_P);
            vectorV = data.initializeVector(size, value, vector_V);

            matrixMR = data.initializeMatrix(size, value, matrix_MR);
            matrixMS = data.initializeMatrix(size, value, matrix_MS);
        }

        //MR * MS
        int[][] multiMatrix = data.multiplyMatrices(matrixMR, matrixMS);

        //O + P + V
        for (int i = 0; i < size; i++) {
            vectorOPV[i] = vectorO[i] + vectorP[i] + vectorV[i];
        }

        //(O + P + V)*(MR * MS)
        int[] finalResultF3 = data.multiplyVectorMatrix(vectorOPV, multiMatrix);

        if (size >= 1000) {
            writeVectorToFile("FinalF3.txt", finalResultF3);
        } else {
            System.out.println("Final result S = " + Arrays.toString(finalResultF3));
        }
        System.out.println("T3 is finished!");

    }
}
