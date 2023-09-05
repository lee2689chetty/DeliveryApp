package com.demo.deliveryapp.model;

public class Pizza {
    public int id, count;
    public String name, desc;
    public float price, totalCost;
    public boolean oneType, hasOption;

    public Pizza(int id, String name, String desc, float price, boolean oneType, boolean hasOption, int count, float totalCost) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.oneType = oneType;
        this.hasOption = hasOption;
        this.count = count;
        this.totalCost = totalCost;
    }

    public float getPrice() {
        return price;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setHasOption(boolean hasOption) {
        this.hasOption = hasOption;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOneType(boolean oneType) {
        this.oneType = oneType;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}

