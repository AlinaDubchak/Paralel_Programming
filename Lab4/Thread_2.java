package org.example;

import java.util.Arrays;

public class Thread_2 extends Thread {
    private Data data; 
    private int threadId; 
    private int start; 
    private int end;
    private int a2;
    private int e2;
    
    public Thread_2(int id, Data D) {
        data = D;
        threadId = id;
        start = (threadId - 1) * data.H; 
        end = threadId * data.H; 
    }

    // Метод для заповнення початкових даних
    private void fillData() {
        for (int i = 0; i < data.N; i++) {
            for (int j = 0; j < data.N; j++) {
                data.MM[i][j] = 1; 
            }
        }
    }
    
    @Override
    public void run() {

        System.out.println("T2 started");
        try {
            fillData(); 

            //Сигнал задачі T1, T3, T4 про введення даних
            data.Synchronized_Monitor.Signal_Input();

            // Чекати на введення даних у потоках T1, T4
            data.Synchronized_Monitor.Wait_Input();

            // Обчислення a2 = max(Zн),
            int[] Z_h = Data.getSubvector(data.Z, start, end);
            a2 = Data.getMaxValueVector(Z_h);

            // Обчислення a = max(a, a2)
            data.Monitor_a.find_Max(a2);         //КД1

            // Сигнал Т1, Т3, Т4 про завершення обчислення а
            data.Synchronized_Monitor.Signal_Max();

            // Чекати на завершення обчислень а у потоках T1, Т3, T4
            data.Synchronized_Monitor.Wait_Max();

            // Копія а2 = а
            a2 = data.Monitor_a.get_a();          //КД2

            // Копія e2 = e
            e2 = data.Monitor_e.get_e();          //КД3
            int[][] MV_h = Data.getSubmatrix(data.MV, start, end);
            int[][] MC_h = Data.getSubmatrix(data.MC, start, end);

            // Обчислення Rн = a*(B*MVн) + e*Xн*(MMн*MC)
            int[] R_h = Data.calculateResult(a2, data.B, MV_h, e2, data.X, data.MM, MC_h, start, end);

            // Запис результату
            Data.insertSubvectorIntoVector(data, R_h, start, end);

            // Чекати на завершення обчислень Rн у потоках T1, Т3, T4
            data.Synchronized_Monitor.Wait_Output();

            // Вивід вектора R як результату
            Data.printVector(data.R);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("T2 finished");
        }
    }
}
