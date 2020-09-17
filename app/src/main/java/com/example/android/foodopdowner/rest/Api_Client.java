package com.example.android.foodopdowner.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Client {
private static Retrofit retrofit= null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit =new Retrofit.Builder()
                .baseUrl("https://hudsonagileventures.com/foodopdnew/api/owner/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit ;
    }
}
