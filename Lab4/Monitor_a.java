package org.example;

public class Monitor_a {
    private int a = Integer.MIN_VALUE;

    public synchronized void find_Max(int b) {
        this.a = Math.max(a, b);
    }

    public synchronized int get_a() {
        return this.a;
    }

}
