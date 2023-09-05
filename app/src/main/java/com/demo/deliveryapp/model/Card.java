package com.demo.deliveryapp.model;

public class Card {
    String number, owner;
    public Card(String number, String owner) {
        this.number = number;
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public String getOwner() {
        return owner;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
