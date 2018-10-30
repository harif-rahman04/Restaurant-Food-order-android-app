package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/10/2017.
 */

public class KotAddons {


    public int id;
    public String kotno;
    public String itemcode;
    public String addon;
    public String rate;
    public String qty;
    public String amount;
    public String status;
    public String localid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getKotno() {
        return kotno;
    }

    public void setKotno(String kotno) {
        this.kotno = kotno;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public KotAddons(String localid,String kotno, String itemcode, String addon, String rate, String qty, String amount, String status) {
        this.localid = localid;
        this.kotno = kotno;
        this.itemcode = itemcode;
        this.addon = addon;
        this.rate = rate;
        this.qty = qty;
        this.amount = amount;
        this.status = status;
    }

    public KotAddons(){  }



    public KotAddons(String localid,String itemcode,String addon,String qty,String amount )
    {
        this.localid = localid;
        this.itemcode = itemcode;
        this.addon = addon;
        this.qty = qty;
        this.amount = amount;
    }

}
