package com.example.round.sqlite_example;

/**
 * Created by Round on 2017-08-31.
 */

public class Person {

    private int id;
    private String name,email;

    public Person(){

    }

    public Person(int id, String name, String emial) {
        this.id = id;
        this.name = name;
        this.email = emial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
