package com.example.android.foodopdowner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CurrentOrdersModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    public List<CurrentOrdersData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CurrentOrdersData> getData() {
        return data;
    }

    public void setData(List<CurrentOrdersData> data) {
        this.data = data;
    }

    public class CurrentOrdersData implements Serializable{

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("tbl_menu_id")
        @Expose
        private String tblMenuId;
        @SerializedName("quantity")
        @Expose
        private String quantity;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("order_type")
        @Expose
        private String orderType;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getTblMenuId() {
            return tblMenuId;
        }

        public void setTblMenuId(String tblMenuId) {
            this.tblMenuId = tblMenuId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

}
