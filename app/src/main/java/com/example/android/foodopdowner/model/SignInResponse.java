
package com.example.android.foodopdowner.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SignInModel> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SignInModel> getData() {
        return data;
    }

    public void setData(List<SignInModel> data) {
        this.data = data;
    }

}
