package org.example;

import java.io.*;
import java.util.*;

public class Data {
    // Enter N
    public int utils() {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true) {
            try {
                System.out.println("Type N:");
                input = Integer.parseInt(scanner.nextLine());
                if (input <= 0) {
                    System.out.println("N should be a positive integer.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
        return input;
    }

    // Enter type of numbers
    public int elem(int size) {
        if (size >= 1000) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Type '1' for randomizing numbers or '2' for constant numbers:");
                int input = scanner.nextInt();
                if (input == 1 || input == 2) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please type '1' or '2'.");
                }
            }
        }
        return 0;
    }


    public int[] initializeVector(int size, int value, String name) {
        Scanner scanner = new Scanner(System.in);
        int[] vector = new int[size];
        if (size == 4) {
            Arrays.fill(vector, value);
        } else {
            for (int i = 0; i < size; i++) {
                while (true) {
                    System.out.print("Enter element for " + name + "[" + (i + 1) + "]: ");
                    if (scanner.hasNextInt()) {
                        vector[i] = scanner.nextInt();
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter an integer.");
                        scanner.next();
                    }
                }
            }
        }
        return vector;
    }

    public int[][] initializeMatrix(int size, int value, String name) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = new int[size][size];
        if (size == 4) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = value;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    while (true) {
                        System.out.println("Enter element for " + name + "[" + i + "][" + j + "]: ");
                        if (scanner.hasNextInt()) {
                            matrix[i][j] = scanner.nextInt();
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter an integer.");
                            scanner.next();
                        }
                    }
                }
            }
        }
        return matrix;
    }

    // Method for multiply Matrix's
    public int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }

    // Method for multiply vector and matrix
    public int[] multiplyVectorMatrix(int[] vector, int[][] matrix) {
        int[] result = new int[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            int sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += vector[j] * matrix[j][i];
            }
            result[i] = sum;
        }
        return result;
    }

    // Method for multiply Vector's
    public int multiplyVectors(int[] vector1, int[] vector2) {
        int dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        return dotProduct;
    }

    // Method for subtract Matrix's
    public int[][] subtractMatrix(int[][] matrixA, int[][] matrixB) {
        int rows = matrixA.length;
        int cols = matrixA[0].length;

        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrixA[i][j] - matrixB[i][j];
            }
        }

        return result;
    }

    // Method SORT
    public int[][] sortRows(int[][] matrix) {
        for (int[] ints : matrix) {
            Arrays.sort(ints);
        }
        return matrix;
    }

    // Method to create a file with a vector containing random or constant values
    public static void createFileWithVector(String fileName, int N, int value, int answer) {
        ArrayList<Integer> vector = new ArrayList<>();

        if(answer == 1){
            int max = 100;
            int min = 1;
            Random random = new Random();
            for (int i = 0; i < N; i++) {
                int randomNumber = random.nextInt(max - min + 1) + min;
                vector.add(randomNumber);
            }
        } else {
            for (int i = 0; i < N; i++) {
                vector.add(value);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int element : vector) {
                writer.write(Integer.toString(element));
                writer.newLine();
            }
            System.out.println("File " + fileName + " successfully created with vector values.");
        } catch (IOException e) {
            System.out.println("Error creating the file: " + e.getMessage());
        }
    }

    // Method to create a file with a matrix containing random or constant values
    public static void createFileWithMatrix(String fileName, int size, int value, int res) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int randomNumber;
                    if (res == 1) {
                        int max = 100;
                        int min = 1;
                        randomNumber = random.nextInt(max - min + 1) + min;
                    } else {
                        randomNumber = value;
                    }
                    writer.write(randomNumber + " ");
                }
                writer.newLine();
            }
            System.out.println("File " + fileName + " successfully created with matrix values.");
        } catch (IOException e) {
            System.out.println("Error creating the file: " + e.getMessage());
        }
    }

    // Method to read a vector from a file
    public static int[] readVectorFromFile(String fileName) {
        ArrayList<Integer> vectorList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by spaces and add each element to the list
                String[] values = line.trim().split("\\s+");
                for (String value : values) {
                    vectorList.add(Integer.parseInt(value));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        // Convert the list to an array
        int[] array = new int[vectorList.size()];
        for (int i = 0; i < vectorList.size(); i++) {
            array[i] = vectorList.get(i);
        }

        return array;
    }

    // Method to read a matrix from a file
    public static int[][] readMatrixFromFile(String fileName) {
        int[][] matrix = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                if (matrix == null) {
                    matrix = new int[values.length][values.length];
                }
                for (int col = 0; col < values.length; col++) {
                    matrix[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return matrix;
    }

    // Method to write a scalar to a file
    public static void writeScalarToFile(String fileName, int vector) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(Integer.toString(vector));
                writer.newLine();
            System.out.println("File " + fileName + " successfully written.");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to write a vector to a file
    public static void writeVectorToFile(String fileName, int[] vector) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int j : vector) {
                writer.write(Integer.toString(j));
                writer.newLine();
            }
            System.out.println("File " + fileName + " successfully written.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to write a matrix to a file
    public static void writeMatrixToFile(String fileName, int[][] matrix) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(Integer.toString(matrix[i][j]));
                    if (j < matrix[i].length - 1) {
                        writer.write(" ");
                    }
                }
                writer.newLine();
            }
            System.out.println("File " + fileName + " successfully written.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
