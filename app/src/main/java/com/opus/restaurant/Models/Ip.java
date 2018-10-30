package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/1/2017.
 */


public class Ip {

    public int id;
    public String ipno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpno() {
        return ipno;
    }

    public void setIpno(String ipno) {
        this.ipno = ipno;
    }

    public Ip(){ }

    public Ip(int id,String ipno){
        this.id = id;
        this.ipno = ipno;
    }

    public Ip(String ipno){
        this.ipno = ipno;
    }
}
