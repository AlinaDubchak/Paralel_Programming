package org.example;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class Thread_4 extends Thread {
    final Data data;
    private int ai;
    private int di;
    final int id = 4;
    public Thread_4( Data d) {

        data = d;
    }

    @Override
    public void run() {

        System.out.println("T4 is started!");

        try {
            data.MO =data.fillMatrix(data.MO);
            data.MR = data.fillMatrix(data.MR);
            data.B = data.fillVector(data.B);
            data.R = data.fillVector(data.R);
            data.Z = data.fillVector(data.Z);
                                                // сигнал про введення даних + очікування, на ввід інших потоків своїх даних
            data.B1.await();                    // бар'єр B1

            int indexStart = (id - 1) * data.H;
            int indexEnd =  id * data.H;

            // Обчислення а4 = Вн * Zн
            int []Bh = Data.getSubvector(data.B,indexStart, indexEnd);
            int []Zh = Data.getSubvector(data.B,indexStart, indexEnd);
            ai = data.multiplyVectors(Bh, Zh);

                                                                    // доступ до спільного ресурсу - КД1
            data.a.updateAndGet(current -> current + ai);          // атомік-змінна a
                                            // сигнал про завершення обчислення а = а + а4
            data.S4.release(3);      // семафор S4

            data.S1.acquire();              // очікування на завершення обчислень а з Т1, Т2, Т3
            data.S2.acquire();              // Семафори S1, S2, S3
            data.S3.acquire();

            int [][] MRh = Data.getSubmatrix(data.MO, indexStart, indexEnd);

            int [][] MEh = Data.multiplyMatrices(data.MO, MRh, indexStart, indexEnd); // Обчислення МЕн = МОн* MR

            int []Ch = Data.multiplyVectorByMatrix(data.R, MEh);                // Обчислення Cн = R * МЕн

                                         // копіювання ai = a --- КД2
            ai = data.CS1();             // критична секція CS1

                                        // копіювання di = d --- КД3
            data.S0.acquire();          // семафор S0
            di = data.d;
            data.S0.release();

            int[] Xh = Data.calculateXh(Ch, Zh, ai, di);             //Обчислення Хн = а4 * d1 * Zн + а4*Сн

            for (int i = indexStart; i < indexEnd; i++) {
                data.X[i] += Xh[i - indexStart];
            }
                                        // Сигнал про завершення обчислення Х
            data.B2.await();            // Бар'єр B2

            System.out.println(Arrays.toString(data.X));             // Виведення результату

        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("T4 finished");
        }

    }
}
