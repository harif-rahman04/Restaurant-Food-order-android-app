package com.opus.restaurant.Models;

import android.support.annotation.NonNull;

/**
 * Created by Admin on 10/31/2017.
 */

public class Tables implements Comparable<Tables> {

    public int tid;
    public int tableid;
    public String tableCode;
    public String tableName;
    public String tablePosition;
    public String chairsCount;
    public String modifiedType;
    private boolean isSectionHeader;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String type;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String floor;

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public String getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(String tablePosition) {
        this.tablePosition = tablePosition;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getChairsCount() {
        return chairsCount;
    }

    public void setChairsCount(String chairsCount) {
        this.chairsCount = chairsCount;
    }

    public Tables(String floor, Object o){}

    public Tables(int tableid, String tableCode,String floor, String tableName, String tablePosition, String chairsCount, String modifiedType) {
        this.tableid = tableid;
        this.tableCode = tableCode;
        this.floor = floor;
        this.tableName = tableName;
        this.tablePosition = tablePosition;
        this.chairsCount = chairsCount;
        this.modifiedType = modifiedType;
    }

    public Tables(int tableid, String tableCode,String floor, String tableName, String tablePosition, String chairsCount, String modifiedType,String type) {
        this.tableid = tableid;
        this.tableCode = tableCode;
        this.floor = floor;
        this.tableName = tableName;
        this.tablePosition = tablePosition;
        this.chairsCount = chairsCount;
        this.modifiedType = modifiedType;
        this.type = type;
    }

    public Tables(){

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
    public int compareTo(@NonNull Tables tables) {
        return this.floor.compareTo(tables.floor);
    }
}
