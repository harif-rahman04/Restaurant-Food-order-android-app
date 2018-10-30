package com.opus.restaurant.Models;

/**
 * Created by Admin on 11/27/2017.
 */

public class BasicDetails {

    int    selected_table_id;
    String selected_table_name;
    String selected_waiter_name;
    String selected_waiter_code;
    String selected_category;
    String no_of_persons;
    String selected_item_code;
    String selected_item_name;
    String Kot_no;
    String order_type;
    String room_type;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getKot_no() {
        return Kot_no;
    }

    public void setKot_no(String kot_no) {
        Kot_no = kot_no;
    }



    public String getSelected_item_code() {
        return selected_item_code;
    }

    public void setSelected_item_code(String selected_item_code) {
        this.selected_item_code = selected_item_code;
    }

    public String getSelected_item_name() {
        return selected_item_name;
    }

    public void setSelected_item_name(String selected_item_name) {
        this.selected_item_name = selected_item_name;
    }

    public String getSelected_item_rate() {
        return selected_item_rate;
    }

    public void setSelected_item_rate(String selected_item_rate) {
        this.selected_item_rate = selected_item_rate;
    }

    String selected_item_rate;


    public String getNo_of_persons() {
        return no_of_persons;
    }

    public void setNo_of_persons(String no_of_persons) {
        this.no_of_persons = no_of_persons;
    }

    public String getSelected_table_name() {
        return selected_table_name;
    }

    public void setSelected_table_name(String selected_table_name) {
        this.selected_table_name = selected_table_name;
    }

    public int getSelected_table_id() {
        return selected_table_id;
    }

    public void setSelected_table_id(int selected_table_id) {
        this.selected_table_id = selected_table_id;
    }

    public String getSelected_waiter_name() {
        return selected_waiter_name;
    }

    public void setSelected_waiter_name(String selected_waiter_name) {
        this.selected_waiter_name = selected_waiter_name;
    }

    public String getSelected_waiter_code() {
        return selected_waiter_code;
    }

    public void setSelected_waiter_code(String selected_waiter_code) {
        this.selected_waiter_code = selected_waiter_code;
    }

    public String getSelected_category() {
        return selected_category;
    }

    public void setSelected_category(String selected_category) {
        this.selected_category = selected_category;
    }

    public  BasicDetails(){

    }

}
