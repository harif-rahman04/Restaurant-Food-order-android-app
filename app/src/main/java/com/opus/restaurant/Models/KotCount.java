package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/21/2017.
 */

public class KotCount {

    public int id;
    public String kotNo;

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String localId;

    public String getKotNo() {
        return kotNo;
    }

    public void setKotNo(String kotNo) {
        this.kotNo = kotNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String createdOn;
    public String status;

    public KotCount(String kotNo, String createdOn, String status) {
        this.kotNo = kotNo;
        this.createdOn = createdOn;
        this.status = status;
    }
    public KotCount(){

    }
}
