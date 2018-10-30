package com.opus.restaurant.Models;

/**
 * Created by Admin on 8/8/2017.
 */

public class CartItemModel {

    public int id;
    public String item_id;
    public String qty;
    public String price;
    public String total_price;
    public String date;
    public String waiter_code;
    public String table_name;
    public String finished_at;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWaiter_code() {
        return waiter_code;
    }

    public void setWaiter_code(String waiter_code) {
        this.waiter_code = waiter_code;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    public String roomtype;
    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String item_name;

    public String getKotno() {
        return kotno;
    }

    public void setKotno(String kotno) {
        this.kotno = kotno;
    }

    public String kotno;

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String item_code;

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String loc_id;
  //  public String loc_id1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public CartItemModel(){
    }

    public CartItemModel(int ids, String itemid, String qtys, String itemprice, String tot){
        this.id=ids;
        this.item_id=itemid;
        this.qty=qtys;
        this.price=itemprice;
        this.total_price=tot;
    }

    public CartItemModel(String itemcode, String qtys, String itemprice, String tot, String locid, String kotno, String itemname){
        this.item_code=itemcode;
        this.qty=qtys;
        this.price=itemprice;
        this.total_price=tot;
        this.loc_id=locid;
        this.kotno=kotno;
        this.item_name = itemname;
    }

    public CartItemModel(String qtys, String localid, int ids){
        this.qty=qtys;
        this.loc_id=localid;
        this.id=ids;
    }

    public CartItemModel(String qtys, String kotno, String itemcode, String totalamt){
        this.qty=qtys;
        this.kotno=kotno;
        this.item_code=itemcode;
        this.total_price = totalamt;
    }

    public CartItemModel(int ids, String qtys, String totalamt){
        this.id=ids;
        this.qty=qtys;
        this.total_price = totalamt;
    }

    public CartItemModel(String item_code, String loc_id){
        this.item_code=item_code;
        this.loc_id=loc_id;

    }

}
