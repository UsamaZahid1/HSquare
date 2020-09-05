package com.example.hsquare;

public class Singleton {

    public static Singleton obj = new Singleton();
    static String guestid;

   static String getId() {
    return guestid;
    }

    private Singleton() {

    }

    public static Singleton getInstance() {

        return obj;
    }


}
