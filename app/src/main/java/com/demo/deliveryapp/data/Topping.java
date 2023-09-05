package com.demo.deliveryapp.data;

import io.realm.RealmObject;

public class Topping extends RealmObject {
    public int smCount, lgCount;
    public KeyPair keyPair;
    public Topping(){
    }

    public Topping(int smCount, int lgCount) {
        this.smCount = smCount;
        this.lgCount = lgCount;
    }

    public double getPrice() {
        return SM.price * smCount + LG.price * lgCount;
    }

    public int getSmCount() {
        return smCount;
    }

    public int getLgCount(){
        return lgCount;
    }
    public static KeyPair SM = new KeyPair("Small", 2.99);
    public static KeyPair LG = new KeyPair("Large", 1.99);
}
