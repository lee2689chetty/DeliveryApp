package com.demo.deliveryapp.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ScrollView;

import static android.content.Context.MODE_PRIVATE;

public class SharedData {
    Context context;
    int count;
    float price;
    public SharedData(Context context){
        this.context = context;
    }

    public void setSharedDB(int count, float price) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getApplicationContext().getPackageName(), MODE_PRIVATE).edit();
        editor.putInt("count", count);
        editor.putFloat("price", price);
        editor.apply();
        return ;
    }

    public int getCountShardDB(){
        SharedPreferences prefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), MODE_PRIVATE);
        int count = prefs.getInt("count", 0);
        return count;
    }

    public float getPriceShardDB() {
        SharedPreferences prefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), MODE_PRIVATE);
        float price = prefs.getFloat("price", 0.0f);
        return price;
    }

    public String getFormartedString(){
        SharedPreferences prefs = context.getSharedPreferences(context.getApplicationContext().getPackageName(), MODE_PRIVATE);
        int count = prefs.getInt("count", 0);
        float price = prefs.getFloat("price", 0.00f);
        String countStr = String.valueOf(count);
        String priceStr = String.format("%.2f",price);
        String str = countStr + " Items:    " + priceStr + "$";
        return str;
    }

    public String getFormartedString(int count, float price){
        String countStr = String.valueOf(count);
        String priceStr = String.format("%.2f",price);
        String str = countStr + " Items:    " + priceStr + "$";
        return str;
    }
}
