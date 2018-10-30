package com.opus.restaurant.Models;

/**
 * Created by Admin on 10/31/2017.
 */

public class User{

    private String username;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username) {
        this.username = username;
    }
    public  User(){}
}
