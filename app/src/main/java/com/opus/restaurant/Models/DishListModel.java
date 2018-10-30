package com.opus.restaurant.Models;

/**
 * Created by Admin on 8/1/2017.
 */

public class DishListModel {

    public int dishid;
    public String dishname;
    public String dishprice;
    public String dishcode;



    public String localid;
    public String kotno;

    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public String getKotno() {
        return kotno;
    }

    public void setKotno(String kotno) {
        this.kotno = kotno;
    }



    public String getDishcode() {
        return dishcode;
    }

    public void setDishcode(String dishcode) {
        this.dishcode = dishcode;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getDishprice() {
        return dishprice;
    }

    public void setDishprice(String dishprice) {
        this.dishprice = dishprice;
    }


    public int getDishid() {
        return dishid;
    }

    public void setDishid(int dishid) {
        this.dishid = dishid;
    }



    public DishListModel(int id, String code, String name, String price) {
        this.dishid = id;
        this.dishcode = code;
        this.dishname = name;
        this.dishprice = price;

    }

    // constructor
    public DishListModel(String code, String name, String price) {

        this.dishcode = code;
        this.dishname = name;
        this.dishprice = price;

    }

    public DishListModel(){

    }

    public DishListModel(String dishname, String dishprice, String dishcode, String localid, String kotno) {
        this.dishname = dishname;
        this.dishprice = dishprice;
        this.dishcode = dishcode;
        this.localid = localid;
        this.kotno = kotno;
    }

}
