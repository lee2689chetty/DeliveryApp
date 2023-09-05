package com.demo.deliveryapp.data;

import io.realm.RealmObject;

public class Size extends RealmObject {



    private double price;


    public double getPrice() {
        return keyPair.price;
    }


    KeyPair keyPair;

    public Size(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public Size() {

    }

    public static KeyPair SMALL = new KeyPair("Small", 2.99);
    public static KeyPair MEDIAM = new KeyPair("Medium", 3.99);
    public static KeyPair LARGE = new KeyPair("Large", 4.99);

    public KeyPair getKeyPair()
    {
        return keyPair;
    }
}