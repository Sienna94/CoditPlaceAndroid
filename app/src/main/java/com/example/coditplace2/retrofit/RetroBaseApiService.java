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
import retrofit2.http.POST;

public interface RetroBaseApiService {
    @FormUrlEncoded
    @POST("PlaceListLocation.do")
    Call<List<ResponseGet>> rPlocation(@FieldMap HashMap<String, String> parameters);
}
