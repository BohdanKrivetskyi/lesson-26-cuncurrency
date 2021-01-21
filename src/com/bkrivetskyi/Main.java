package com.bkrivetskyi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int MAX_CAUNT = 3000;
    private int countInt = 0;
    private AtomicInteger countAtomic = new AtomicInteger(0);

    public static void main(String[] args) {


    }

    public void count() {

        ExecutorService executor = Executors.newFixedThreadPool(300);
        int countExpect = (int) (Math.random() * MAX_CAUNT + 2000);

        CountDownLatch doneSignal = new CountDownLatch(countExpect);

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                countInt++;
                countAtomic.getAndIncrement();
                doneSignal.countDown();
            }
        };
        System.out.println(countExpect);

        for (int i = 0; i < countExpect; i++) {
            executor.submit(task);
        }
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        System.out.println("Counter int: " + countInt);
        System.out.println("Counter atomic: " + countAtomic);
    }
}
