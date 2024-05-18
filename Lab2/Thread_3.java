package org.example;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

public class Thread_3 extends Thread {
    private Data data;
    private int ai;
    private int di;
    final int id = 3;
    public Thread_3( Data d) {
        data = d;
    }

    @Override
    public void run() {

        System.out.println("T3 is started!");

        try {
            data.MO =data.fillMatrix(data.MO);
            data.MR = data.fillMatrix(data.MR);
            data.B = data.fillVector(data.B);
            data.R = data.fillVector(data.R);
            data.Z = data.fillVector(data.Z);
                                            // сигнал про введення даних + очікування, на ввід інших потоків своїх даних
            data.B1.await();                // бар'єр B1

            int indexStart = (id - 1) * data.H;
            int indexEnd =  id * data.H;

            // Обчислення а3 = Вн * Zн
            int []Bh = Data.getSubvector(data.B,indexStart, indexEnd);
            int []Zh = Data.getSubvector(data.B,indexStart, indexEnd);
            ai = data.multiplyVectors(Bh, Zh);
                                                                    // доступ до спільного ресурсу - КД1
            data.a.updateAndGet(current -> current + ai);          // атомік-змінна a
                                             // сигнал про завершення обчислення а = а + а3
            data.S3.release(3);      // семафор S3

            data.S1.acquire();              // очікування на завершення обчислень а з Т1, Т2, Т4
            data.S2.acquire();              // Семафори S1, S2, S4
            data.S4.acquire();

            int [][] MRh = Data.getSubmatrix(data.MO, indexStart, indexEnd);

            int [][] MEh = Data.multiplyMatrices(data.MO, MRh, indexStart, indexEnd); // Обчислення МЕн = МОн* MR

            int []Ch = Data.multiplyVectorByMatrix(data.R, MEh);                // Обчислення Cн = R * МЕн

                                          // копіювання ai = a --- КД2
            ai = data.CS1();             // критична секція CS1

                                         // копіювання di = d --- КД3
            data.S0.acquire();          // семафор S0
            di = data.d;
            data.S0.release();

            int[] Xh = Data.calculateXh(Ch, Zh, ai, di);             //Обчислення Хн = а3 * d1 * Zн + а3*Сн

            for (int i = indexStart; i < indexEnd; i++) {
                data.X[i] += Xh[i - indexStart];
            }
                                        // Сигнал про завершення обчислення Х
            data.B2.await();            // Бар'єр B2

        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        finally {
            System.out.println("T3 finished");
        }

    }
}
