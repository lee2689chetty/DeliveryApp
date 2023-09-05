package com.demo.deliveryapp.data;

import io.realm.RealmObject;

public class Sauces extends RealmObject {


    private double price;

    Sauces(double price) {
        this.price = price;
    }

    public double getPrice() {
        return keyPair.price;
    }


    KeyPair keyPair;

    public Sauces(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Sauces() {

    }


    public static KeyPair SM = new KeyPair("Small+", 2.99);
    public static KeyPair LG = new KeyPair("Large+", 3.99);
    public static KeyPair XL = new KeyPair("Large++", 4.99);
    public static KeyPair XXL = new KeyPair("Large+++", 6.99);


    public KeyPair getKeyPair() {
        return keyPair;
    }
}
