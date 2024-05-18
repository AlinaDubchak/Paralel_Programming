package org.example;


public class Thread_1 extends Thread {
    private Data data; 
    private int threadId; 
    private int start; 
    private int end; 
    private int a1;
    private int e1;
    
    public Thread_1(int id, Data D) {
        data = D;
        threadId = id;
        start = (threadId - 1) * data.H; 
        end = threadId * data.H;
    }

    // Метод для заповнення початкових даних
    private void fillData() {
        for (int i = 0; i < data.N; i++) {
            for (int j = 0; j < data.N; j++) {
                data.MV[i][j] = 1; 
                data.MC[i][j] = 1; 
            }
        }
    }
    
    @Override
    public void run() {
        System.out.println("T1 started");
        try {
            fillData(); 

            //Сигнал задачі T2, T3, T4 про введення даних
            data.Synchronized_Monitor.Signal_Input();

            // Чекати на введення даних у потоках T2, T4
            data.Synchronized_Monitor.Wait_Input();

            // Обчислення a1 = max(Zн),
            int[] Z_h = Data.getSubvector(data.Z, start, end);
            a1 = Data.getMaxValueVector(Z_h);

            // Обчислення a = max(a, a1)
            data.Monitor_a.find_Max(a1);         //КД1

            // Сигнал Т2, Т3, Т4 про завершення обчислення а
            data.Synchronized_Monitor.Signal_Max();

            // Чекати на завершення обчислень а у потоках T2, Т3, T4
            data.Synchronized_Monitor.Wait_Max();
            // Копія а1 = а
            a1 = data.Monitor_a.get_a();          //КД2

            // Копія e1 = e
            e1 = data.Monitor_e.get_e();          //КД3

            int[][] MV_h = Data.getSubmatrix(data.MV, start, end);
            int[][] MC_h = Data.getSubmatrix(data.MC, start, end);

            // Обчислення Rн = a*(B*MVн) + e*Xн*(MMн*MC)
            int[] R_h = Data.calculateResult(a1, data.B, MV_h, e1, data.X, data.MM, MC_h, start, end);

            // Запис результату
            Data.insertSubvectorIntoVector(data, R_h, start, end);

            // Сигнал про завершення обчислень Rн Т2
            data.Synchronized_Monitor.Signal_Output();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("T1 finished");
        }
    }
}
