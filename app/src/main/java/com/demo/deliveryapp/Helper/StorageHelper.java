package com.demo.deliveryapp.Helper;

import android.util.Log;

import com.demo.deliveryapp.data.Adress;
import com.demo.deliveryapp.data.Product;

import java.util.List;

import io.realm.Realm;

public class StorageHelper {

    private static Realm realm;

    private static Realm getRealm() {
        if (realm == null)
            realm = Realm.getDefaultInstance();
        return realm;
    }

    public static List<Product> getCartItems() {
        return getRealm().where(Product.class).sort(Product.INDEX).findAll();
    }

    public static void addToCart(Product product) {
        product.finalzie();
        Product existing = getRealm().where(Product.class).equalTo(Product.ID, product.getId()).findFirst();
        getRealm().beginTransaction();
        if (existing != null) {
            existing.addQuantity(product.getQuantity());
        } else {
            getRealm().copyToRealm(product);
        }
        getRealm().commitTransaction();
    }

    public static void reduceQuantity(Product product) {
        getRealm().beginTransaction();
        product.recudeQuantity();
        Log.e("Quantity: ",product.getQuantity()+"");
        if(product.getQuantity()==0)
            product.deleteFromRealm();
        getRealm().commitTransaction();
    }

    public static void increaseQuantity(Product product) {

        getRealm().beginTransaction();
        product.increaseQuantity();
        getRealm().commitTransaction();
    }

    public static void clearCart() {
        getRealm().beginTransaction();
        getRealm().delete(Product.class);
        getRealm().commitTransaction();
    }

    public static long  getCartSize() {
       List<Product> list = getRealm().where(Product.class).sort(Product.INDEX).findAll();
       long counter =0;
       for(Product product: list)
           counter+=  product.getQuantity();
       return counter;
    }

    public static long getAdressCount() {
       return getRealm().where(Adress.class).count();
    }

    public static List<Adress> getAdresses() {
        return  getRealm().where(Adress.class).sort(Adress.ID).findAll();
    }

    public static void saveAdress(Adress adress) {
        getRealm().beginTransaction();
        getRealm().copyToRealmOrUpdate(adress);
        getRealm().commitTransaction();
    }

    public static void removeAdress(Adress adress) {
        getRealm().beginTransaction();
        adress.deleteFromRealm();
        getRealm().commitTransaction();
    }
}
