package com.opus.restaurant.Models;

public class EditTablesDetails {

    public int id;
    public String tableName;
    public String orderType;
public String roomType;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getNoofPersons() {
        return noofPersons;
    }

    public void setNoofPersons(String noofPersons) {
        this.noofPersons = noofPersons;
    }

    public String noofPersons;

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String waiterName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getServerKot() {
        return serverKot;
    }

    public void setServerKot(String serverKot) {
        this.serverKot = serverKot;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String serverKot;
    public String localId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;
}
