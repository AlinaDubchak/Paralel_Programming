package org.example;

//Лабороторна робота ЛР4 Варіант 21
//R = max(Z)*(B*MV) + e*X*(MM*MC)
//T1 – MV, MC
//T2 – MM, R
//T3 –  -
//T4 –  B,X, e, Z
//Дубчак Аліна Євгеніївна ІМ-13
//Дата 28 04 2024


public class Lab4 {
    public static void main(String[] args) {
        Data D = new Data();

        Thread_1 T1 = new Thread_1(1, D);
        Thread_2 T2 = new Thread_2(2, D);
        Thread_3 T3 = new Thread_3(3, D);
        Thread_4 T4 = new Thread_4(4, D);

        T1.start();
        T2.start();
        T3.start();
        T4.start();

        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
