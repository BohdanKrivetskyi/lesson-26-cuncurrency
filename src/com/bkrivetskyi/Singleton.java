package com.bkrivetskyi;

public class Singleton {

    private static Singleton instance;
    private static int countInt;


    public Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        System.out.println("Count int: " + countInt++);
        return instance;
    }
}

