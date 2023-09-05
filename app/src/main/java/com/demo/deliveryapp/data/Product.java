package com.demo.deliveryapp.data;

import android.util.Log;

import com.demo.deliveryapp.Helper.StorageHelper;

import java.security.Key;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {

    public static String TYPE_PIZZA = "PIZZA", TYPE_COMBO = "COMBO", TYPE_SIDES = "SIDES", TYPE_VEBERAGE = "VEBERAGE";

    public static String INDEX = "index", ID = "id";
    //String type = TYPE_PIZZA;


    Size size;
    Crust crust;
    Sauces sauces;
    Topping topping;
    RealmList<KeyPair> topics = new RealmList<>();
    String title = "Farm House", description = "A pizza that goes ballistic on veggies! Check out this mouth swatering overload of cunchy, crips capsicum, succulent,etc";

    public boolean isTopicSet() {
        return topics.size() == 0 ? false : true;
    }

    public Product() {

    }

    public Product(boolean custom) {
        this.size = new Size(Size.MEDIAM);
        this.crust = new Crust(Crust.NORMAL);
        this.topics.add(Topic.CORN);
    }

    public Product(String name, String desc, Size size, Crust crust) {
        this.title = name;
        this.description = desc;
        this.size = size;
        this.crust = crust != null ? crust : new Crust(Crust.NORMAL);
        this.topics.add(Topic.CORN);
    }

    public Product(String name, Size size, Sauces sauces, Topping topping) {
        this.title = name;
        this.size = size;
        this.sauces = sauces;
        this.topping = topping;
    }


    long index = 0;
    @PrimaryKey
    String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void finalzie() {
        index = System.currentTimeMillis();
        String topicNames = "";
        for (KeyPair topic : topics)
            topicNames += topic.name;
        if (crust != null) {
            id = title + description + size.keyPair.name + crust.keyPair.name + topicNames;
        } else if (topping != null) {
            id = title + size.keyPair.name;
            if (sauces != null)
                id += sauces.keyPair.name;
            id += topping.getLgCount() + topping.getSmCount();
        }
    }

    int quantity = 1;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }


    public double getPrice() {
        long price = 0;
        for (KeyPair topic : topics) {
            price += topic.getPrice();
        }
        if (crust != null)
            return (size.getPrice() + crust.getPrice() + price) * quantity;
        else if (sauces != null) {
            return (size.getPrice() + sauces.getPrice() + topping.getPrice()) * quantity;
        } else {
            return (size.getPrice() + topping.getPrice()) * quantity;
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public String getFlavour() {

        StringBuilder builder = new StringBuilder();
        builder.append("Size: ");
        builder.append(size.keyPair.name);
        builder.append(" | Crust: ");
        builder.append(crust.keyPair.name);
        builder.append(" | Topic:");

        int top1 = 0, top2 = 0, top3 = 0, top4 = 0, top5 = 0;
        Log.e("KeyPair", "" + topics.size());
        for (KeyPair keyPair : topics) {

            if (keyPair.name.equalsIgnoreCase(Topic.TOMATO.name))
                top1++;
            else if (keyPair.name.equalsIgnoreCase(Topic.CORN.name))
                top2++;
            else if (keyPair.name.equalsIgnoreCase(Topic.JALAPENOS.name))
                top3++;
            else if (keyPair.name.equalsIgnoreCase(Topic.PINEAPPLE.name))
                top4++;
            else if (keyPair.name.equalsIgnoreCase(Topic.PAPPERONI.name))
                top5++;
        }

        if (top1 > 0) {
            builder.append(" ");
            builder.append(Topic.TOMATO.name);
            builder.append(String.format("(X%d) ,", top1));
        }

        if (top2 > 0) {
            builder.append(" ");
            builder.append(Topic.CORN.name);
            builder.append(String.format("(X%d) ,", top2));
        }

        if (top3 > 0) {
            builder.append(" ");
            builder.append(Topic.JALAPENOS.name);
            builder.append(String.format("(X%d) ,", top3));
        }

        if (top4 > 0) {
            builder.append(" ");
            builder.append(Topic.PINEAPPLE.name);
            builder.append(String.format("(X%d) ,", top4));
        }

        if (top5 > 0) {
            builder.append(" ");
            builder.append(Topic.PAPPERONI.name);
            builder.append(String.format("(X%d)", top5));
        }
        String result = builder.toString();

        if (result.charAt(result.length() - 1) == ',')
            result = result.substring(0, result.length() - 1);
        return result;
    }

    public void recudeQuantity() {
        quantity--;
    }

    public String getOptionString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Size: ");
        builder.append(size.keyPair.name);
        if (sauces != null) {
            builder.append("  Sauces: ");
            builder.append(sauces.keyPair.name);
        }
        if (topping != null) {
            builder.append("  Topping: ");
            if (topping.lgCount != 0) {
                builder.append("Lg ");
                builder.append(topping.lgCount);
                builder.append(", ");
            }
            if (topping.smCount != 0) {
                builder.append("Sm ");
                builder.append(topping.smCount);
            }
        }
        String result = builder.toString();
        if (result.charAt(result.length() - 1) == ',')
            result = result.substring(0, result.length() - 1);
        return result;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void addTopic(KeyPair topic) {
        int count = 0;
        for (KeyPair keyPair : topics)
            if (keyPair == topic)
                count++;

        if (count > 1) {
            topics.remove(topic);
            topics.remove(topic);
        } else
            topics.add(topic);
    }


    public boolean hasTopic(KeyPair topic) {
        return topics.contains(topic);
    }

    public int topicCount(KeyPair topic) {
        int count = 0;
        for (KeyPair keyPair : topics)
            if (keyPair == topic)
                count++;
        return count;
    }
}
