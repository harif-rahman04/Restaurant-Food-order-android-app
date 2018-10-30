package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/10/2017.
 */

public class KotTables {

    public int id;
    public String kotno;
    public String waitercode;
    public String noofperson;
    public String startedAt;
    public String finishedAt;
    public String invoiceNo;
    public String modifiedDate;
    public String modifiedType;
    public String roomType;
    public String localid;
public KotTables(){

}
    public KotTables(String kotno, String waitercode, String noofperson, String startedAt, String finishedAt, String invoiceNo, String modifiedDate, String modifiedType, String roomType, String localid) {
        this.kotno = kotno;
        this.waitercode = waitercode;
        this.noofperson = noofperson;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.invoiceNo = invoiceNo;
        this.modifiedDate = modifiedDate;
        this.modifiedType = modifiedType;
        this.roomType = roomType;
        this.localid = localid;
    }

    public KotTables(String kotno, String waitercode, String noofperson, String startedAt, String finishedAt, String invoiceNo, String modifiedDate, String modifiedType, String roomType) {
        this.kotno = kotno;
        this.waitercode = waitercode;
        this.noofperson = noofperson;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.invoiceNo = invoiceNo;
        this.modifiedDate = modifiedDate;
        this.modifiedType = modifiedType;
        this.roomType = roomType;
    }

    public KotTables(String kotno, String waitercode, String noofperson, String startedAt, String finishedAt, String invoiceNo, String roomType, String localid) {
        this.kotno = kotno;
        this.waitercode = waitercode;
        this.noofperson = noofperson;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.invoiceNo = invoiceNo;
        this.roomType = roomType;
        this.localid = localid;
    }

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

    public String getWaitercode() {
        return waitercode;
    }

    public void setWaitercode(String waitercode) {
        this.waitercode = waitercode;
    }

    public String getNoofperson() {
        return noofperson;
    }

    public void setNoofperson(String noofperson) {
        this.noofperson = noofperson;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public String getLocalid() {
        return localid;
    }

    public void setLocalid(String localid) {
        this.localid = localid;
    }

}
