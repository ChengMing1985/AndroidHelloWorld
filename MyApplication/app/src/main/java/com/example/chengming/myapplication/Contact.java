package com.example.chengming.myapplication;

/**
 * Created by ChengMing on 2016/12/6.
 */

public class Contact {
    private String name;
    private String number;
    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
}
