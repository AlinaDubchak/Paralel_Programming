package org.example;

import java.util.Arrays;

import static org.example.Data.*;
import static org.example.Data.readMatrixFromFile;

public class Thread_F2 extends Thread {
    private final int size;
    private final int value;
    private final int res ;

    public Thread_F2(int size, int value, int res) {
        this.size = size;
        this.value = value;
        this.res = res;
    }

    @Override
    public void run() {
        System.out.println("T2 is started!");
        Data data = new Data();

        int[][] matrixMH, matrixMK, matrixMF;
        String matrix_MH = "matrix MH", matrix_MK = "matrix MK", matrix_MF = "matrix MF";

        if (size >=1000){
            String fileNameMH = "matrixMH.txt";
            String fileNameMK = "matrixMK.txt";
            String fileNameMF = "matrixMF.txt";

            createFileWithMatrix(fileNameMH, size, value, res);
            createFileWithMatrix(fileNameMK, size, value, res);
            createFileWithMatrix(fileNameMF, size, value, res);

            matrixMH = readMatrixFromFile(fileNameMH);
            matrixMK = readMatrixFromFile(fileNameMK);
            matrixMF = readMatrixFromFile(fileNameMF);
        } else {
            matrixMH = data.initializeMatrix(size, value, matrix_MH);
            matrixMK = data.initializeMatrix(size, value, matrix_MK);
            matrixMF = data.initializeMatrix(size, value, matrix_MF);
        }

        // MH * MK
        int[][] matrixRes = data.multiplyMatrices(matrixMH, matrixMK);

        // MF - MH * MK
        int[][] matrixSub = data.subtractMatrix(matrixMF, matrixRes);

        if (size >= 1000) {
            // SORT(MF - MH * MK)(?)
            writeMatrixToFile("FinalF2.txt", data.sortRows(matrixSub));
        } else {
            System.out.println("Final result MG = " + Arrays.deepToString(data.sortRows(matrixSub)));
        }

        System.out.println("T2 is finished!");
    }
}
