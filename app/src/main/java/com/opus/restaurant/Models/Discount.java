package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/20/2017.
 */

public class Discount {

    public int id;
    public int discountId;
    public String date;
    public String type;
    public String description;
    public String amount;
    public String modifiedtype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getModifiedtype() {
        return modifiedtype;
    }

    public void setModifiedtype(String modifiedtype) {
        this.modifiedtype = modifiedtype;
    }

    public Discount(int discountId, String date, String type, String description, String amount, String modifiedtype) {
        this.discountId = discountId;
        this.date = date;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.modifiedtype = modifiedtype;
    }


}
