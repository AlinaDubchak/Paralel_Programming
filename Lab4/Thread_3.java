package org.example;


public class Thread_3 extends Thread {
    private Data data;
    private int threadId; 
    private int start; 
    private int end; 
    private int a3;
    private int e3;
    
    public Thread_3(int id, Data D) {
        data = D;
        threadId = id;
        start = (threadId - 1) * data.H; 
        end = threadId * data.H; 
    }

    @Override
    public void run() {
        System.out.println("T3 started");
        try {

            // Чекати на введення даних у потоках T1, T4, T2
            data.Synchronized_Monitor.Wait_Input();

            // Обчислення a3 = max(Zн),
            int[] Z_h = Data.getSubvector(data.Z, start, end);
            a3 = Data.getMaxValueVector(Z_h);

            // Обчислення a = max(a, a3)
            data.Monitor_a.find_Max(a3);         //КД1

            int[][] MV_h = Data.getSubmatrix(data.MV, start, end);
            int[][] MC_h = Data.getSubmatrix(data.MC, start, end);

            // Сигнал Т2, Т1, Т4 про завершення обчислення а
            data.Synchronized_Monitor.Signal_Max();

            // Чекати на завершення обчислень а у потоках T2, Т1, T4
            data.Synchronized_Monitor.Wait_Max();

            // Копія а3 = а
            a3 = data.Monitor_a.get_a();          //КД2

            // Копія e3 = e
            e3 = data.Monitor_e.get_e();          //КД3

            // Обчислення Rн = a*(B*MVн) + e*Xн*(MMн*MC)
            int[] R_h = Data.calculateResult(a3, data.B, MV_h, e3, data.X, data.MM, MC_h, start, end);

            // Запис результату
            Data.insertSubvectorIntoVector(data, R_h, start, end);

            // Сигнал про завершення обчислень Rн Т2
            data.Synchronized_Monitor.Signal_Output();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("T3 finished");
        }
    }
}
