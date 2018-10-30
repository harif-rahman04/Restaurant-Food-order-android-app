package com.opus.restaurant.Models;

/**
 * Created by Admin on 10/31/2017.
 */

public class Items {

    public int id;
    public int itemId;
    public String date;
    public String itemCode;
    public String itemName;
    public String cost;
    public String category;
    public String description;
    public String type;
    public String modifiedType;

    public String getAc_rate() {
        return ac_rate;
    }

    public void setAc_rate(String ac_rate) {
        this.ac_rate = ac_rate;
    }

    public String getNormal_rate() {
        return normal_rate;
    }

    public void setNormal_rate(String normal_rate) {
        this.normal_rate = normal_rate;
    }

    public String ac_rate;
    public String normal_rate;


    public String getKotype() {
        return kotype;
    }

    public void setKotype(String kotype) {
        this.kotype = kotype;
    }

    public String kotype;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }


      // Constructors

    public Items(){}

    public Items(int itemId, String date, String itemCode, String itemName, String cost, String category, String description, String type, String modifiedType,String kottype) {
        this.itemId = itemId;
        this.date = date;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.cost = cost;
        this.category = category;
        this.description = description;
        this.type = type;
        this.modifiedType = modifiedType;
        this.kotype = kottype;
    }


    public Items(int itemId, String date, String itemCode, String itemName, String cost, String category, String description, String type, String modifiedType,String kottype,String normal_rate,String ac_rate) {
        this.itemId = itemId;
        this.date = date;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.cost = cost;
        this.category = category;
        this.description = description;
        this.type = type;
        this.modifiedType = modifiedType;
        this.kotype = kottype;
        this.normal_rate = normal_rate;
        this.ac_rate = ac_rate;
    }


   }
