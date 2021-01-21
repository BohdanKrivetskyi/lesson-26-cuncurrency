package com.bkrivetskyi;

import java.util.concurrent.atomic.AtomicInteger;

public class SingletonSynchron {
    private static volatile SingletonSynchron instance;
    private static AtomicInteger atomicInt;

    public SingletonSynchron() {
        atomicInt = new AtomicInteger(0);
    }

    public static SingletonSynchron getInstance() {
        SingletonSynchron localInst = instance;
        if (localInst == null) {
            synchronized (SingletonSynchron.class) {
                localInst = instance;
                if (localInst == null) {
                    instance = localInst = new SingletonSynchron();
                }
            }
        }
        System.out.println("Atomic counter " + atomicInt.incrementAndGet());
        return localInst;
    }
}
