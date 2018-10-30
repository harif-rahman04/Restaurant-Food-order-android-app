package com.opus.restaurant.Models;

import java.util.ArrayList;

/**
 * Created by Admin on 11/28/2017.
 */

public class KotOrdersView {

    public int id;
    public int itemId;
    public String itemName;
    public String itemCode;
    public String amount;
    public String itemQty;
    public String itemRate;
    public int addonId;
    public String addonCode;
    public String addonName;
    public String addonAmount;
    public String addonQty;
    public String addonRate;
    String Suggestion;

    public String getSuggestion() {
        return Suggestion;
    }

    public void setSuggestion(String suggestion) {
        Suggestion = suggestion;
    }

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public String getAddonRate() {
        return addonRate;
    }

    public void setAddonRate(String addonRate) {
        this.addonRate = addonRate;
    }

    public ArrayList<KotAddonList> getAddonlist() {
        return addonlist;
    }

    public void setAddonlist(ArrayList<KotAddonList> addonlist) {
        this.addonlist = addonlist;
    }

    public ArrayList<KotAddonList>addonlist;

    public String getAddonCode() {
        return addonCode;
    }

    public void setAddonCode(String addonCode) {
        this.addonCode = addonCode;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public int getAddonId() {
        return addonId;
    }

    public void setAddonId(int addonId) {
        this.addonId = addonId;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public String getAddonAmount() {
        return addonAmount;
    }

    public void setAddonAmount(String addonAmount) {
        this.addonAmount = addonAmount;
    }

    public String getAddonQty() {
        return addonQty;
    }

    public void setAddonQty(String addonQty) {
        this.addonQty = addonQty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }


}
