package com.demo.deliveryapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Adress extends RealmObject {

    public static final String _SELF = "Adress", ID = "id", TITLE = "title", ADRESS_LINE_ONE = "adressLineOne", ADRESS_LINE_TWO = "adressLineTwo", CITY = "city", POSTAL_CODE = "postalCode";

    @PrimaryKey
    long id;

    String title = "", adressLineOne = "", adressLineTwo = "", city = "", postalCode = "";

    public Adress() {
        this.id = System.currentTimeMillis();
    }

    public Adress(String title, String adressLineOne, String adressLineTwo, String city, String postalCode) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.adressLineOne = adressLineOne;
        this.adressLineTwo = adressLineTwo;
        this.city = city;
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdressLineOne() {
        return adressLineOne;
    }

    public void setAdressLineOne(String adressLineOne) {
        this.adressLineOne = adressLineOne;
    }

    public String getAdressLineTwo() {
        return adressLineTwo;
    }

    public void setAdressLineTwo(String adressLineTwo) {
        this.adressLineTwo = adressLineTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAdress() {
        return adressLineOne + ", " + adressLineTwo + ", " + city + ", " + postalCode;
    }
}
