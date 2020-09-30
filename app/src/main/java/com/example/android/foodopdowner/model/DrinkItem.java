package com.example.android.foodopdowner.model;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrinkItem implements Serializable {

    @SerializedName("id")
    public String id;

    @SerializedName("item_name")

    public String item_name = "";
    @SerializedName("item_description")
    public String item_description = "";
    @SerializedName("item_type")
    public String item_type = "";
    @SerializedName("quantity")
    public String quantity = "";

    @SerializedName("original_price")
    public String original_price = "";

    @SerializedName("special_price")
    public String special_price = "";

    @SerializedName("item_image")
    public Uri item_image;
    @SerializedName("is_active")
    public String is_active = "";
    @SerializedName("from_date")
    public String from_date = "";
    @SerializedName("created_by")
    public String created_by = "";
    @SerializedName("modified_date")
    public String modified_date = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }

    public Uri getItem_image() {
        return item_image;
    }

    public void setItem_image(Uri item_image) {
        this.item_image = item_image;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
}
