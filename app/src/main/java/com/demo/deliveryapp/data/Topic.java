package com.demo.deliveryapp.data;

import io.realm.RealmObject;

public class Topic extends RealmObject {


    public Topic() {
    }

    KeyPair keyPair;

    public Topic(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public static KeyPair TOMATO = new KeyPair("TOMATO", 2.99);
    public static KeyPair CORN = new KeyPair("CORN", 3.99);
    public static KeyPair JALAPENOS = new KeyPair("JALAPENOS", 4.99);
    public static KeyPair PINEAPPLE = new KeyPair("PINEAPPLE", 4.99);
    public static KeyPair PAPPERONI = new KeyPair("PAPPERONI", 4.99);


    public double getPrice() {
        return keyPair.price;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}
