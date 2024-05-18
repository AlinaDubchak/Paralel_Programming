// Лабороторна робота ЛР5 Варіант 6
// A = (R*MC)*MD*p + (B*Z)*E*d
//
// T1: X, e
// T2: C, MA
// T3: R, MD
// T4: B, p
//
// Дубчак Аліна Євгеніївна, група ІМ-13
// Дата 14 05 2024

using namespace std;

#include <iostream>
#include <omp.h>
#include <chrono>

int *readVector(int size);

int **readMatrix(int n);

void printVector(int *v, int size);

void printMatrix(int **matrix, int rows, int cols);

void insertSubVectorIntoVector(int *subVector, int startIndex, int endIndex, int *S, int statusVector);

int *multiplyVectorAndMatrix(int *vector, int **matrix);

int *getSubVector(int *v, int start, int size);

int vectorMultiplication(int *v1, int *v2, int size);

int **getSubMatrix(int **matrix, int startRow, int startCol, int size);

int *calculateRes(int *S, int** MDh, int p_i, int a_i, int *Eh, int d_i);

const int P = 4;
const int N = 16;
const int H = N / P;


int main() {
    cout << "Lab5 started!" << endl;
    auto start = chrono::high_resolution_clock::now();

    //глобальні змінні
    int d;
    int p;
    int a = 0;
    int *Z;
    int *R;
    int *B;
    int *E;
    int *S = new int[N];
    int *A = new int[N];
    int *S_MDh;
    int **MC;
    int **MD;

    //локальні змінні
    int T_id;
    int p_i;
    int a_i;
    int d_i;

    omp_set_num_threads(P);

#pragma omp parallel private(T_id, p_i, a_i, d_i) shared(d, a, p, Z, R, B, E, A, MC, MD)
    {
        T_id = omp_get_thread_num() + 1;
#pragma omp critical
        cout << "T" << T_id << " started" << endl;

        switch (T_id) {
            case 1: //T1
                E = readVector(N); //Введення E
                MC = readMatrix(N); //Введення MC
                break;
            case 2: //T2
                d = 1; //Введення d
                MD = readMatrix(N); //Введення MD
                break;
            case 3: //T3
                B = readVector(N); //Введення B
                p = 1; //Введення p
                break;
            case 4: //T4
                R = readVector(N); //Введення R
                Z = readVector(N); //Введення Z

                break;
            default:
                break;
        }
#pragma omp barrier                       //Синхронізація по введенню

        int *Bh = getSubVector(B, (T_id - 1) * H, H); // Отримати субвектор Bh
        int *Zh = getSubVector(Z, (T_id - 1) * H, H); // Отримати субвектор Zh

        a_i = vectorMultiplication(Bh, Zh, H);


#pragma omp critical(CS)                 //CS1 - Обчислення 2  a = a + ai
        {
            a += a_i;
        }

#pragma omp barrier               //Синхронізація по Обчисленню 2  a = a + ai


        int **MCh = getSubMatrix(MC, N, (T_id - 1) * H, T_id * H); // Отримати суматрицю MCh
        int *Sh = multiplyVectorAndMatrix(R, MCh);
        insertSubVectorIntoVector(Sh, (T_id - 1) * H, T_id * H, S, 1);

#pragma omp barrier               //Синхронізація по Обчисленню 3  S =R*MCн

#pragma omp critical(CS) // Копія ai = a
        {
            a_i = a; // CS2
        }

#pragma omp critical(CS)  // Копія di = d
        {
            d_i = d; // CS3
        }

#pragma omp critical(CS) // Копія pi = p
        {
            p_i = p; // CS4
        }

        int **MDh = getSubMatrix(MD, N, (T_id - 1) * H, T_id * H); // Отримати суматрицю MDh
        int *Eh = getSubVector(E, (T_id - 1) * H, H); // Отримати субвектор Eh

        int *Ah = calculateRes(S, MDh, p_i, a_i, Eh, d_i); //??

#pragma omp barrier               //Синхронізація по Обчисленню 4  A = S*MDн*pi + ai*Eн*di

        insertSubVectorIntoVector(Ah, (T_id - 1) * H, T_id * H, A, 1);

        if (T_id == 1) {
#pragma omp critical
            {
                cout << "A: " << endl;
                printVector(A, N);
            }
        }

        // Звільнення пам'яті
        delete [] Bh;
        delete [] Zh;
        delete [] Sh;
        delete [] Eh;
        delete [] Ah;

        for (int i = 0; i < N; ++i) {
            delete[] MCh[i];
            delete[] MDh[i];
        }
#pragma omp critical

        cout << "T" << T_id << " finished" << endl;
    }

    // Звільнення пам'яті
    delete[] S;
    delete[] A;
    delete [] S_MDh;
    delete[] Z;
    delete[] R;
    delete[] B;
    delete[] E;

    for (int i = 0; i < N; ++i) {
        delete[] MC[i];
        delete[] MD[i];
    }

    auto stop = chrono::high_resolution_clock::now();
    auto duration = chrono::duration_cast<chrono::milliseconds>(stop - start);
    cout << "Time:  " << duration.count() << " ms" << endl;
    return 0;
}

int *readVector(int size) {
    int *v = new int[size];
    for (int i = 0; i < size; ++i)
        v[i] = 1;
    return v;
}

int **readMatrix(int n) {
    int **m = new int *[n];
    for (int i = 0; i < n; ++i)
        m[i] = readVector(n);
    return m;
}

void printVector(int *v, int size) {
    for (int i = 0; i < size; ++i)
        cout << v[i] << " ";
    cout << endl;
}

void printMatrix(int **matrix, int rows, int cols) {
    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < cols; ++j) {
            cout << matrix[i][j] << " ";
        }
        cout << endl;
    }
}

int **getSubMatrix(int **matrix, int rowCount, int startColumn, int endColumn) {
    int columnCount = endColumn - startColumn;
    int **submatrix = new int *[rowCount];
    for (int i = 0; i < rowCount; ++i) {
        submatrix[i] = new int[columnCount];
        for (int j = startColumn; j < endColumn; ++j) {
            submatrix[i][j - startColumn] = matrix[i][j];
        }
    }
    return submatrix;
}

int *getSubVector(int *v, int start, int size) {
    int *subVector = new int[size];
    for (int i = 0; i < size; ++i) {
        subVector[i] = v[start + i];
    }
    return subVector;
}

int vectorMultiplication(int *v1, int *v2, int size) {
    int result = 0;
    for (int i = 0; i < size; ++i) {
        result += v1[i] * v2[i];
    }
    return result;
}

int *multiplyVectorAndMatrix(int *vector, int **matrix) {
    int *result = new int[H];

    for (int i = 0; i < H; i++) {
        int sum = 0;
        for (int j = 0; j < N; j++) {
            sum += matrix[j][i] * vector[j];
        }
        result[i] = sum;
    }

    return result;
}

void insertSubVectorIntoVector(int *subVector, int startIndex, int endIndex, int *S, int statusVector) {
    for (int i = startIndex; i < endIndex; i++) {
        if (statusVector == 0) {
            subVector[i] = subVector[i - startIndex];
        } else {
            S[i] = subVector[i - startIndex];
        }
    }
}

int *calculateRes(int *S, int** MDh, int p_i, int a_i, int *Eh, int d_i) {
    int *S_MD = multiplyVectorAndMatrix(S, MDh);
    int *result = new int[N];
    for (int i = 0; i < N; ++i) {
        result[i] = S_MD[i] * p_i + a_i * Eh[i] * d_i;
    }
    delete[] S_MD;
    return result;
}
