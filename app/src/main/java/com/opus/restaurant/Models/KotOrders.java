package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/25/2017.
 */

public class KotOrders {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String ordertype;
    public String localId;
    public String tableName;
    public String kot;
    public String waiterCode;
    public String noOfPerson;
    public String itemCode;
    public String itemName;
    public String itemRate;
    public String addOnAmount;
    public String qty;
    public String amount;
    public String startedAt;
    public String finishedAt;
    public String invoiceNo;
    public String modifiedDate;
    public String modifiedType;
    public String roomType;
    public String suggestion;

    public KotOrders(String localId, String tableName, String kot, String waiterCode, String noOfPerson, String itemCode, String itemName, String itemRate, String addOnAmount, String qty, String amount, String startedAt, String finishedAt, String invoiceNo, String modifiedDate, String modifiedType, String roomType, String suggestion,String ordertype) {
        this.localId = localId;
        this.tableName = tableName;
        this.kot = kot;
        this.waiterCode = waiterCode;
        this.noOfPerson = noOfPerson;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemRate = itemRate;
        this.addOnAmount = addOnAmount;
        this.qty = qty;
        this.amount = amount;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.invoiceNo = invoiceNo;           ////////INOVICE NO..............
        this.modifiedDate = modifiedDate;
        this.modifiedType = modifiedType;
        this.roomType = roomType;
        this.suggestion = suggestion;
        this.ordertype=ordertype;
    }

    public KotOrders(String qty,String invoiceNo,String itemCode,String amount,String suggestion){
        this.qty = qty;
        this.invoiceNo = invoiceNo;
        this.itemCode = itemCode;
        this.amount = amount;
        this.suggestion=suggestion;
    }
    public KotOrders(String finishedAt,String invoiceNo){
        this.finishedAt = finishedAt;
        this.invoiceNo = invoiceNo;

    }
    public  KotOrders(String Suggestion,String itemCode,String kot_no){
        this.suggestion=Suggestion;
        this.itemCode=itemCode;
        this.invoiceNo=kot_no;
    }

    public KotOrders(){ }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKot() {
        return kot;
    }

    public void setKot(String kot) {
        this.kot = kot;
    }

    public String getWaiterCode() {
        return waiterCode;
    }

    public void setWaiterCode(String waiterCode) {
        this.waiterCode = waiterCode;
    }

    public String getNoOfPerson() {
        return noOfPerson;
    }

    public void setNoOfPerson(String noOfPerson) {
        this.noOfPerson = noOfPerson;
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

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
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

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

}
