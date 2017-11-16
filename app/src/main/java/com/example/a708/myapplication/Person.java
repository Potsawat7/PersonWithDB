package com.example.a708.myapplication;

/**
 * Created by 708 on 10/27/2017.
 */

public class Person {
    private String nickName;
    private String firstName;
    private String surName;
    public Person(String n, String fn , String sn){
        this.nickName = n;
        this.firstName = fn;
        this.surName = sn;
    }

    public String getNickName() {
        return nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

}
