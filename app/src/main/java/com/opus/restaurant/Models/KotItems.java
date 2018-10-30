package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/10/2017.
 */

public class KotItems {

    public int id;
    public String kotno;
    public String itemCode;
    public String itemName;
    public String rate;
    public String addOnAmount;
    public String qty;
    public String amount;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAddOnAmount() {
        return addOnAmount;
    }

    public void setAddOnAmount(String addOnAmount) {
        this.addOnAmount = addOnAmount;
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

    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

    public KotItems(String kotno, String itemCode, String itemName, String rate, String addOnAmount, String qty, String amount, String localid) {
        this.kotno = kotno;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.rate = rate;
        this.addOnAmount = addOnAmount;
        this.qty = qty;
        this.amount = amount;
        this.localid = localid;
    }

    public KotItems(String itemCode, String itemName, String rate, String addOnAmount, String qty, String amount, String localid) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.rate = rate;
        this.addOnAmount = addOnAmount;
        this.qty = qty;
        this.amount = amount;
        this.localid = localid;
    }



}
