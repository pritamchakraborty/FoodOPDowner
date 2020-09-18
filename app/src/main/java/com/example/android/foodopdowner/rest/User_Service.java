package com.example.android.foodopdowner.rest;

import com.example.android.foodopdowner.model.Data;
import com.example.android.foodopdowner.model.Drinks_Item;
import com.example.android.foodopdowner.model.EmailData;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.model.SignupData;
import com.example.android.foodopdowner.model.SignupModel;
import com.example.android.foodopdowner.model.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface User_Service {

    @FormUrlEncoded
    @POST("owner_signup")
    Call<SignupData>signUp(@Field("user_category")String user_category, @Field("first_name")String first_name, @Field("last_name")String last_name,
                           @Field("email")String email, @Field("password")String password, @Field("phone_no")String phone_no);

    //address details
    @FormUrlEncoded
    @POST("owner_business_setup")
    Call<JsonObject>buisness_setup(@Field("owner_id")String owner_id,@Field("bussiness_id")String bussiness_id,@Field("bussiness_name")String bussiness_name,@Field("address")String address,@Field("street")String street,
                                      @Field("city")String city,@Field("phone_no")String phone_no,@Field("country")String country,@Field("delivary_boy_pin")String delivary_boy_pin,@Field("business_logo")String business_logo, @Field("pin")String pin);

    @FormUrlEncoded
    @POST("owner_login")
    Call<JsonObject> ownerlogin(@Field("email")String email_id,@Field("password") String password);



    //Menu_Items
    @GET("customer_api.php?")
    Call<Food_Items>doGetUserList(@Query("apicall")String page);

    //Drinks Item
    @GET("customer_api.php?")
    Call<Drinks_Item>drinksList(@Query("apicall")String page);

    @FormUrlEncoded
    @POST("email_validation")
    Call<EmailData>getEmail(@Field("email") String email);


    @FormUrlEncoded
    @POST("franchise")
    Call<JsonObject>sendFranchisedetail(@Field("owner_id")String owner_id,@Field("business_id")String business_id,@Field("franchise_status")String franchise_status,@Field("franchise_name")String franchise_name,
                                        @Field("franchise_email")String franchise_email, @Field("store_id")String store_id,
                                        @Field("franchise_contact_person")String franchise_contact_person,@Field("franchise_phone")String franchise_phone,
                                        @Field("sales_tax")String sales_tax,@Field("surcharge")String surcharge,@Field("transaction_fee")String transaction_fee);

}
