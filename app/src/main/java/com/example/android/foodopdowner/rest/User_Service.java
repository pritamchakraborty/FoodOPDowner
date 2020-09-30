package com.example.android.foodopdowner.rest;

import android.database.Observable;

import com.example.android.foodopdowner.model.CurrentOrdersModel;
import com.example.android.foodopdowner.model.Drinks_Item;
import com.example.android.foodopdowner.model.EmailData;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.model.SignInResponse;
import com.example.android.foodopdowner.model.SignupData;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface User_Service {

    @FormUrlEncoded
    @POST("owner_signup")
    Call<SignupData>signUp(@Field("user_category")String user_category, @Field("first_name")String first_name, @Field("last_name")String last_name,
                           @Field("email")String email, @Field("password")String password, @Field("phone_no")String phone_no);

//    @Multipart
//    @FormUrlEncoded
//    @POST("owner_business_setup")
//    Call<JsonObject>buisness_setup(@Field("owner_id")String owner_id,@Field("business_id")String bussiness_id,@Field("business_name")String bussiness_name,@Field("address")String address,@Field("street")String street,
//                                      @Field("city")String city,@Field("phone_no")String phone_no,@Field("country")String country,@Field("delivary_boy_pin")String delivary_boy_pin,
//                                   @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Field("pin")String pin);
    @Multipart
    @POST("owner_business_setup")
    Call<JsonObject>buisness_setup(@Part("owner_id")String owner_id,
                                          @Part("business_id")String bussiness_id,
                                          @Part("business_name")String bussiness_name,
                                          @Part("address")String address,
                                          @Part("street")String street,
                                          @Part("city")String city,
                                          @Part("phone_no")String phone_no,
                                          @Part("country")String country,
                                          @Part("delivary_boy_pin")String delivary_boy_pin,
                                          @Part MultipartBody.Part image,
                                          @Part("pin")String pin);

    //@POST("owner_business_setup")
    //Call<JsonObject>buisness_setup(@Body RequestBody file);

    @FormUrlEncoded
    @POST("owner_login")
    Call<SignInResponse> ownerlogin(@Field("email")String email_id, @Field("password") String password);



    //Menu_Items
    @GET("customer_api.php?")
    Call<Food_Items>doGetUserList(@Query("apicall")String page);

    //Drinks Item
    @GET("customer_api.php?")
    Call<Drinks_Item>drinksList(@Query("apicall")String page);

    @FormUrlEncoded
    @POST("email_validation")
    Call<EmailData>getEmail(@Field("email") String email);

    //delivery boy
    @FormUrlEncoded
    @POST("delivary_boy_add")
    Call<JsonObject>deliveryBoy(@Field("owner_id") String owner_id, @Field("business_id") String business_id,
                                @Field("first_name") String first_name, @Field("last_name") String last_name,
                                @Field("phone_number") String phone_number,@Field("delivery_boy_status") String delivery_boy_status,
                                @Field("delivary_boy_pin")String delivary_boy_pin);


    //Bank Setup
    @FormUrlEncoded
    @POST("add_bank")
    Call<JsonObject>getBank(@Field("owner_id") String owner_id, @Field("business_id") String business_id,
                            @Field("bank_name") String bank_name, @Field("acc_number") String acc_number,
                            @Field("routing_number") String routing_number, @Field("city") String city,
                            @Field("setup_fee") String setup_fee, @Field("txn_id") String txn_id,
                            @Field("payment_gross") String payment_gross, @Field("currency_code") String currency_code,
                            @Field("onwer_email") String onwer_email, @Field("payment_status") String payment_status);


    @FormUrlEncoded
    @POST("franchise")
    Call<JsonObject>sendFranchisedetail(@Field("owner_id")String owner_id,@Field("business_id")String business_id,@Field("franchise_status")String franchise_status,@Field("franchise_name")String franchise_name,
                                        @Field("franchise_email")String franchise_email, @Field("store_id")String store_id,
                                        @Field("franchise_contact_person")String franchise_contact_person,@Field("franchise_phone")String franchise_phone,
                                        @Field("sales_tax")String sales_tax,@Field("surcharge")String surcharge,@Field("transaction_fee")String transaction_fee);

    @FormUrlEncoded
    @POST("table_menu")
    Call<Food_Items>saveFoodMenu(@Field("menu_type") String menu_type, @Field("drink")String drink,
                                 @Field("business_id")String business_id,
                                 @Field("owner_id")String owner_id,
                                 @Field("menu_name")String menu_name,@Field("price")String price,
                                 @Field("pic")String pic, @Field("discount")String discount);

    @FormUrlEncoded
    @POST("current_order")
    Call<CurrentOrdersModel>currentOrder(@Field("business_id") String business_id, @Field("owner_id")String owner_id);

}
