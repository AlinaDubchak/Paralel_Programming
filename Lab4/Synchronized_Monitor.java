package org.example;

public class Synchronized_Monitor {
    private int Flag1 = 0;
    private int Flag2 = 0;
    private int Flag3 = 0;

    public synchronized void Wait_Input() {
        try {
            if (Flag1 < 3) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void Signal_Input() {
        Flag1 += 1;
        if (Flag1 == 3) {
            notifyAll();
        }
    }

    public synchronized void Wait_Max() {
        try {
            if (Flag2 < 4) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public synchronized void Signal_Max() {
        Flag2 += 1;
        if (Flag2 == 4) {
            notifyAll();
        }
    }

    public synchronized void Wait_Output() {
        try {
            if (Flag3 < 2) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void Signal_Output() {
        Flag3 += 1;
        if (Flag3 == 3) {
            notify();
        }
    }
}
