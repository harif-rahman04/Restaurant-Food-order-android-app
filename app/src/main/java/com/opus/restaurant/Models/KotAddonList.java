package com.opus.restaurant.Models;

/**
 * Created by Admin on 12/4/2017.
 */

public class KotAddonList extends KotOrdersView {

    public int addonId;
    public String addonCode;

    public int getAddonId() {
        return addonId;
    }

    public void setAddonId(int addonId) {
        this.addonId = addonId;
    }

    public String getAddonCode() {
        return addonCode;
    }

    public void setAddonCode(String addonCode) {
        this.addonCode = addonCode;
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

    public String addonName;
    public String addonAmount;
    public String addonQty;

}
