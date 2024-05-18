package org.example;


public class Thread_4 extends Thread {
    private Data data; 
    private int threadId; 
    private int start; 
    private int end; 
    private int a4;
    private int e4;
    
    public Thread_4(int id, Data D) {
        data = D;
        threadId = id;
        start = (threadId - 1) * data.H; 
        end = threadId * data.H; 
    }
    
    private void fillData() {
        data.Monitor_e.set_e(1);
        for (int i = 0; i < data.N; i++) {
            data.B[i] = 1; 
            data.X[i] = 1; 
            data.Z[i] = 1;
        }
    }
    
    @Override
    public void run() {
        System.out.println("T4 started");
        try {
            fillData(); 

            //Сигнал задачі T1, T3, T2 про введення даних
            data.Synchronized_Monitor.Signal_Input();

            // Чекати на введення даних у потоках T1, T2
            data.Synchronized_Monitor.Wait_Input();

            // Обчислення a4 = max(Zн),
            int[] Z_h = Data.getSubvector(data.Z, start, end);
            a4 = Data.getMaxValueVector(Z_h);

            // Обчислення a = max(a, a4)
            data.Monitor_a.find_Max(a4);         //КД1

            // Сигнал Т2, Т3, Т1 про завершення обчислення а
            data.Synchronized_Monitor.Signal_Max();

            // Чекати на завершення обчислень а у потоках T2, Т3, T1
            data.Synchronized_Monitor.Wait_Max();

            // Копія а4 = а
            a4 = data.Monitor_a.get_a();          //КД2

            // Копія e4 = e
            e4 = data.Monitor_e.get_e();          //КД3

            int[][] MV_h = Data.getSubmatrix(data.MV, start, end);
            int[][] MC_h = Data.getSubmatrix(data.MC, start, end);

            // Обчислення Rн = a*(B*MVн) + e*Xн*(MMн*MC)
            int[] R_h = Data.calculateResult(a4, data.B, MV_h, e4, data.X, data.MM, MC_h, start, end);

            // Запис результату
            Data.insertSubvectorIntoVector(data, R_h, start, end);

            // Сигнал про завершення обчислень Rн Т2
            data.Synchronized_Monitor.Signal_Output();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("T4 finished");
        }
    }
}
