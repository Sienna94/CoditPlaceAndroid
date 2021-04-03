package com.example.coditplace2.retrofit;

import android.content.Context;
import android.util.Log;

import com.example.coditplace2.Storage;
import com.example.coditplace2.retrofit.responseBody.ResponseGet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    public static final String BASE_URL = Storage.HOME_URL + ":8180/oop/";
    private static RetroBaseApiService retroBaseApiService =null;

    public static RetroBaseApiService getRetroBaseApiService(){
        if(retroBaseApiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retroBaseApiService = retrofit.create(RetroBaseApiService.class);
        }
        Log.d("retrofit", "getRetroBaseApiService: returned");
        return retroBaseApiService;
    }
}
