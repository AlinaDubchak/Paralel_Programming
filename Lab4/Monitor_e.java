package org.example;

public class Monitor_e {
    private int e;

    public synchronized void set_e(int e) {
        this.e = e;
    }

    public synchronized int get_e () {
        return this.e;
    }

}
