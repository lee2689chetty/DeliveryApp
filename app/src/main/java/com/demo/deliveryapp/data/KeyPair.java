package com.demo.deliveryapp.data;

import io.realm.RealmObject;

public class KeyPair extends RealmObject {

    double price=0;
    String name = "";


    public KeyPair() {
    }

    public KeyPair(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
       return name;
    }

    public double getPrice()
    {
        return price;
    }
}
