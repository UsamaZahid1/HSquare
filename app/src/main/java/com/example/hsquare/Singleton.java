package com.example.hsquare;

public class Singleton {

    public static Singleton obj = new Singleton();
    public static String guestid,googleId,fbId;

   static String getId() {
    return guestid;
    }

    private Singleton() {

    }

    public static Singleton getInstance() {

        return obj;
    }


}
