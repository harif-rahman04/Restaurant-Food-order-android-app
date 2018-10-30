package com.opus.restaurant.Models;

/**
 * Created by Admin on 8/17/2017.
 */

public class KeyValue {
    public String key;
    public String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public KeyValue(String key, String value){
        this.key=key;
        this.value=value;
    }
}
