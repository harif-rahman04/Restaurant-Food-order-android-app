package com.opus.restaurant.Models;

/**
 * Created by Admin on 8/09/2017.
 */

public class KotNoListModel {

    public int id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String total_amount;

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String local_id;
    public String kot_no;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKot_no() {
        return kot_no;
    }

    public void setKot_no(String kot_no) {
        this.kot_no = kot_no;
    }

    public KotNoListModel(){
    }

    public KotNoListModel(String loc_id, String k_no, String stat) {
        this.local_id = loc_id;
        this.kot_no = k_no;
        this.status=stat;
    }

    public String table_name;
    public String waiter_name;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public String order_type;
    public String server_id;

}
