package com.bkrivetskyi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadLock {

    public Object lock1 = new Object();
    public Object lock2 = new Object();

    public static void main(String[] args) {
        new DeadLock().run();
    }

    private void run() {
        DeadLock1 deadLock1 = new DeadLock1();
        DeadLock2 deadLock2 = new DeadLock2();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(deadLock1);
        executor.submit(deadLock2);
        executor.shutdown();
    }

    private class DeadLock1 implements Runnable {

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("deadlock#1: start");
                synchronized (lock2) {
                    System.out.println("deadlock#1: continue");
                }
            }
        }
    }

    private class DeadLock2 implements Runnable {

        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("deadlock#2: start");
                synchronized (lock1) {
                    System.out.println("deadlock#2: continue");
                }
            }
        }
    }
}
