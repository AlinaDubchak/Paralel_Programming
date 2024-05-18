package org.example;

// Лабораторна робота №2. Варіант 20
// X = (B*Z)*(d*Z + R*(MO*MR))
// T1 – MO
// T2 – Z, R
// T3 –  B, MR
// T4 –  X, d
// Дубчак Аліна Євгеніївна, група ІМ-13
// Дата 25.03.2024

public class Lab2 {
    public static void main(String[] args) {
        Data data = new Data();

        Thread_1 T1 = new Thread_1(data);
        Thread_2 T2 = new Thread_2(data);
        Thread_3 T3 = new Thread_3(data);
        Thread_4 T4 = new Thread_4(data);

        T1.start();
        T2.start();
        T3.start();
        T4.start();

    }
}
