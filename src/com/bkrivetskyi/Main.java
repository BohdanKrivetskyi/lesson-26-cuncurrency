package com.bkrivetskyi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int MAX_CAUNT = 30000;
    private int countInt = 0;
    private AtomicInteger countAtomic = new AtomicInteger(0);

    public static void main(String[] args) {
       // new Main().count();
        new Main().singleton();
    }

    public void count() {

        ExecutorService executor = Executors.newFixedThreadPool(200);
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

        public void singleton() {
            CountDownLatch doneSignal = new CountDownLatch(2000);
            ExecutorService executor = Executors.newFixedThreadPool(2000);

            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    Singleton.getInstance();
                    SingletonSynchron.getInstance();
                    doneSignal.countDown();
                }
            };
            for (int i = 0; i < 2000; i++) {
                executor.submit(task);
            }
            try {
                doneSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.shutdown();
        }
}
