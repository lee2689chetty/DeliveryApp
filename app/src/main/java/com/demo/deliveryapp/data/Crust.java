package com.demo.deliveryapp.data;

import io.realm.RealmObject;

public class Crust extends RealmObject {


    private double price;

    Crust(double price) {
        this.price = price;
    }

    public double getPrice() {
        return keyPair.price;
    }


    KeyPair keyPair;

    public Crust(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Crust() {

    }


    public static KeyPair CHEESY = new KeyPair("CHEESY", 2.99);
    public static KeyPair NORMAL = new KeyPair("NORMAL", 3.99);
    public static KeyPair SAUSSAGE = new KeyPair("SAUSSAGE", 4.99);
    public static KeyPair ITALIAN = new KeyPair("ITALIAN", 6.99);
    public static KeyPair STUFFED = new KeyPair("STUFFED", 8.99);


    public KeyPair getKeyPair() {
        return keyPair;
    }
}
