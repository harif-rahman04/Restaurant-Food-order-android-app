package com.opus.restaurant.Models;

/**
 * Created by Admin on 10/31/2017.
 */

public class Category {

    public int cid;
    public int categoryId;
    public String category;
    public String modifiedType;
    public String type;

    public Category(int categoryId, String category,String type, String modifiedType) {
        this.categoryId = categoryId;
        this.category = category;
        this.type = type;
        this.modifiedType = modifiedType;
    }

    public Category() { }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModifiedType() {
        return modifiedType;
    }

    public void setModifiedType(String modifiedType) {
        this.modifiedType = modifiedType;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

}
