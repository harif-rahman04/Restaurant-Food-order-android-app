package com.opus.restaurant.Models;

/**
 * Created by Admin on 10/31/2017.
 */

public class Waiters {

    public int wid;
    public String waiterCode;
    public String waiterName;
    public String mobileNo;
    public String idProof;
    public String idProofNo;
    public String modifiedType;

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String getIdProofNo() {
        return idProofNo;
    }

    public void setIdProofNo(String idProofNo) {
        this.idProofNo = idProofNo;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWaiterCode() {
        return waiterCode;
    }

    public void setWaiterCode(String waiterCode) {
        this.waiterCode = waiterCode;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Waiters(){}

    public Waiters(String waiterCode, String waiterName, String mobileNo, String idProof, String idProofNo, String modifiedType) {
        this.waiterCode = waiterCode;
        this.waiterName = waiterName;
        this.mobileNo = mobileNo;
        this.idProof = idProof;
        this.idProofNo = idProofNo;
        this.modifiedType = modifiedType;
    }

}
