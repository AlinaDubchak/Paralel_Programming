// Лабораторна робота №1. Варіант 5
// F1 = d = ((A + B)* (C *(MA*ME)))
// F2 = MG = SORT(MF - MH * MK)
// F3 = S = (O + P + V)*(MR * MS)
// Дубчак Аліна Євгеніївна, група ІМ-13
// Дата 16.02.2024

public class Lab1 {
    public static void main(String[] args) {
        Data data = new Data();
        int size = data.utils();
        int res = data.elem(size);

        int valueForT1 = 1;
        int valueForT2 = 2;
        int valueForT3 = 3;

        Thread_F1 T1 = new Thread_F1(size, valueForT1, res);
        Thread_F2 T2 = new Thread_F2(size, valueForT2, res);
        Thread_F3 T3 = new Thread_F3(size, valueForT3, res);

        T1.start();
        T2.start();
        T3.start();

        try {
            T1.join();
            T2.join();
            T3.join();
        } catch (InterruptedException e){
            System.out.println(e);
        }
    }
}
