package com.opus.restaurant.Models;

import android.support.annotation.NonNull;

/**
 * Created by Admin on 11/24/2017.
 */

public class AddonOptionalCell implements Comparable<AddonOptionalCell> {

    public int id;
    public int addonid;
    public String date;
    public String itemName;
    public String priority;
    public String groupName;
    public String addon;
    public String amount;
    public String type;
    public String itemType;
    public String modifiedType;
    private boolean isSectionHeader;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddonid() {
        return addonid;
    }

    public void setAddonid(int addonid) {
        this.addonid = addonid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }


    public AddonOptionalCell(){

    }

    public void setToSectionHeader()
    {

        isSectionHeader = true;
    }

    public boolean isSectionHeader()
    {
        return isSectionHeader;
    }



    @Override
    public int compareTo(@NonNull AddonOptionalCell addonOptionalCell) {
        return this.groupName.compareTo(addonOptionalCell.groupName);
    }
}
