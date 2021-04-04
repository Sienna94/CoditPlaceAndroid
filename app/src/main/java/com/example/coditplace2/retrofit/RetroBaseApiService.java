package com.example.coditplace2.retrofit;

import com.example.coditplace2.Storage;
import com.example.coditplace2.retrofit.responseBody.ResponseGet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetroBaseApiService {
    @POST("PlaceList.do")
    Call<List<ResponseGet>> rPlaceList();

    @FormUrlEncoded
    @POST("PlaceListLocation.do")
    Call<List<ResponseGet>> rPlocation(@FieldMap HashMap<String, String> parameters);

    @POST("PlaceListName.do")
    Call<List<ResponseGet>> rPname(@FieldMap HashMap<String, String> parameters);

    @POST("PlaceListAddress.do")
    Call<List<ResponseGet>> rPaddress(@FieldMap HashMap<String, String> parameters);
}
